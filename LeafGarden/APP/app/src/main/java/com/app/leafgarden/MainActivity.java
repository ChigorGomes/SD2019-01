package com.app.leafgarden;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import BD.BancoDeDados;
import Classe.Usuario;
import DAO.UsuarioDAO;

public class MainActivity extends AppCompatActivity {

    TextView entrarComGoogle;
    TextView novaConta;
    private BancoDeDados bancoDeDados;
    private SQLiteDatabase database;
    EditText email;
    EditText senha;
    Button buttonEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrarComGoogle = (TextView) findViewById(R.id.textViewGoogle);
        novaConta = (TextView) findViewById(R.id.textViewNovaConta);
        email = (EditText) findViewById(R.id.editTextEmailLogin);
        senha = (EditText) findViewById(R.id.editTextSenhaLogin);
        buttonEntrar= (Button) findViewById(R.id.buttonEntrar);

        try{
            bancoDeDados= new BancoDeDados(this);
            database= bancoDeDados.getReadableDatabase();
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("Conexão realizada com sucesso!");
            builder.setNeutralButton("Ok",null);
            builder.show();
        }catch (SQLException e){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("Conexão não realizada!");
            builder.setNeutralButton("Ok",null);
            builder.show();
        }



//        /*Link para criar conta com o Google*/
//        SpannableString spannableString = new SpannableString("Entrar com o Google");
//        spannableString.setSpan(new customOnclickGoogle(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        entrarComGoogle.setText(spannableString);
//        entrarComGoogle.setMovementMethod(LinkMovementMethod.getInstance());

        /*Link para criar conta normal*/

         SpannableString spannableString = new SpannableString("Criar Conta");
        spannableString.setSpan(new custonOnclickNovaConta(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        novaConta.setText(spannableString);
        novaConta.setMovementMethod(LinkMovementMethod.getInstance());

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"preencha o campo email", Toast.LENGTH_SHORT).show();

                }else if(senha.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"preencha o campo senha", Toast.LENGTH_SHORT).show();

                }else{
                    UsuarioDAO usuarioDAO= new UsuarioDAO(MainActivity.this);
                    Usuario usuario= usuarioDAO.getUsuario(email.getText().toString().trim(),senha.getText().toString().trim());
                    if(usuario!=null){
                        Intent intent= new Intent(MainActivity.this, TelaMenu.class);
                        intent.putExtra("usuario",usuario);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Usuário e/ou Senha inválidos",Toast.LENGTH_SHORT).show();

                    }
                }
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
            textPaint.setColor(Color.BLUE);
            textPaint.setUnderlineText(true);
        }
    }
}