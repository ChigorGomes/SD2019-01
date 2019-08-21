package com.app.leafgarden.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView editTextresetSenha;
    Button buttonNovaConta;
    EditText editTextemail;
    EditText editTextsenha;
    Button buttonEntrar;
    public static  final String PREFS_NAME="leafGardenFile";
    FirebaseAuth firebaseAuth;
    SharedPreferences setting=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextresetSenha = findViewById(R.id.textViewResetSenha);
        buttonNovaConta =  findViewById(R.id.textViewNovaConta);
        editTextemail = (EditText) findViewById(R.id.editTextEmailLoginEditar);
        editTextsenha = (EditText) findViewById(R.id.editTextSenhaLoginEditar);
        buttonEntrar= (Button) findViewById(R.id.buttonEntrar);
        setting =   getSharedPreferences(PREFS_NAME,0);

        editTextemail.setText(setting.getString("email",""));
        editTextsenha.setText(setting.getString("senha",""));


        firebaseAuth = FirebaseAuth.getInstance();

        /*Link para criar conta com o Google*/
        SpannableString spannableString = new SpannableString("Esqueci minha senha");
        spannableString.setSpan(new custonOnclickResetSenha(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editTextresetSenha.setText(spannableString);
        editTextresetSenha.setMovementMethod(LinkMovementMethod.getInstance());



        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaUsuario();

            }
        });



        buttonNovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,TelaCadastroLogin.class);
                startActivity(intent);
            }
        });




    }



    private void verificaUsuario(){
        String email = editTextemail.getText().toString().trim();
        String senha = editTextsenha.getText().toString().trim();

        if(email.isEmpty()){
            editTextemail.setError("Preencha o campo E-mail!");
            editTextemail.requestFocus();
        }else if(isValidEmail(email)==false){
            editTextemail.setError("Formato de e-mail inválido!");
            editTextemail.requestFocus();
        }else if(senha.isEmpty()){
            editTextsenha.setError("Preencha o campo Senha!");
            editTextsenha.requestFocus();
        }else if(senha.length()<8){
            editTextsenha.setError("Senha no minímo 8 caracteres!");
            editTextsenha.requestFocus();
        }

        else{
            firebaseAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SharedPreferences.Editor  editor= setting.edit();
                        editor.putString("email",editTextemail.getText().toString().trim());
                        editor.putString("senha",editTextsenha.getText().toString().trim());
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, TelaMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Usuário/senha não encntrado",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    class custonOnclickResetSenha extends ClickableSpan {
        @Override
        public void onClick(View texView) {
            Intent intent= new Intent(MainActivity.this,ResetarSenha.class);
            startActivity(intent);

        }

        public void updateDrawState(TextPaint textPaint) {
            textPaint.setColor(Color.BLACK);
            textPaint.setUnderlineText(true);
        }
    }

    private  boolean isValidEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){ return true;}
        return  false;
    }



}