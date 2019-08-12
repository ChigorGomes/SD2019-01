package com.app.leafgarden;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView cadastroPlanta;
    TextView novaConta;
    EditText editTextemail;
    EditText editTextsenha;
    Button buttonEntrar;
    public static  final String PREFS_NAME="leafGardenFile";
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadastroPlanta = (TextView) findViewById(R.id.textViewCadastroPlanta);
        novaConta = (TextView) findViewById(R.id.textViewNovaConta);
        editTextemail = (EditText) findViewById(R.id.editTextEmailLoginEditar);
        editTextsenha = (EditText) findViewById(R.id.editTextSenhaLoginEditar);
        buttonEntrar= (Button) findViewById(R.id.buttonEntrar);
        final SharedPreferences setting=  getSharedPreferences(PREFS_NAME,0);

        editTextemail.setText(setting.getString("email",""));
        editTextsenha.setText(setting.getString("senha",""));


        firebaseAuth = FirebaseAuth.getInstance();



        /*Link para criar conta com o Google*/
        SpannableString spannableString = new SpannableString("CADASTRAR PLANTAS");
        spannableString.setSpan(new custonOnclickCadastroPlanta(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cadastroPlanta.setText(spannableString);
        cadastroPlanta.setMovementMethod(LinkMovementMethod.getInstance());

        /*Link para criar conta normal*/

        spannableString = new SpannableString("Cadastre-se");
        spannableString.setSpan(new custonOnclickNovaConta(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        novaConta.setText(spannableString);
        novaConta.setMovementMethod(LinkMovementMethod.getInstance());

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaUsuario();

            }
        });


    }

    class custonOnclickNovaConta extends ClickableSpan {
        @Override
        public void onClick(View texView) {
            Intent intent= new Intent(MainActivity.this,TelaCadastroLogin.class);
            startActivity(intent);

        }

        public void updateDrawState(TextPaint textPaint) {
//            textPaint.setColor(Color.BLACK);
//            textPaint.setUnderlineText(true);
        }
    }


    class custonOnclickCadastroPlanta extends ClickableSpan {
        @Override
        public void onClick(View texView) {
            Intent intent= new Intent(MainActivity.this,CadastraPlanta.class);
            startActivity(intent);

        }
//
        public void updateDrawState(TextPaint textPaint) {
//            textPaint.setColor(Color.BLUE);
//            textPaint.setUnderlineText(true);
        }
    }

    private void verificaUsuario(){
        String email = editTextemail.getText().toString().trim();
        String senha = editTextsenha.getText().toString().trim();

        if(email.isEmpty()){
            editTextemail.setError("Preencha o campo Email!");
            editTextemail.requestFocus();
        }if(senha.isEmpty()){
            editTextsenha.setError("Preencha o campo Senha!");
            editTextsenha.requestFocus();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(MainActivity.this, TelaMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }




}