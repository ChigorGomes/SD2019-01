package com.app.leafgarden;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import BD.BancoDeDados;
import Classe.Usuario;
import DAO.UsuarioDAO;

public class MainActivity extends AppCompatActivity {

    TextView cadastroPlanta;
    TextView novaConta;
    private BancoDeDados bancoDeDados;
    private SQLiteDatabase database;
    EditText email;
    EditText senha;
    Button buttonEntrar;
    public static  final String PREFS_NAME="leafGardenFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadastroPlanta = (TextView) findViewById(R.id.textViewCadastroPlanta);
        novaConta = (TextView) findViewById(R.id.textViewNovaConta);
        email = (EditText) findViewById(R.id.editTextEmailLoginEditar);
        senha = (EditText) findViewById(R.id.editTextSenhaLoginEditar);
        buttonEntrar= (Button) findViewById(R.id.buttonEntrar);
        final SharedPreferences setting=  getSharedPreferences(PREFS_NAME,0);

        email.setText(setting.getString("email",""));
        senha.setText(setting.getString("senha",""));

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



        /*Link para criar conta com o Google*/
        SpannableString spannableString = new SpannableString("");
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
                if(email.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"preencha o campo email", Toast.LENGTH_SHORT).show();

                }else if(senha.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"preencha o campo senha", Toast.LENGTH_SHORT).show();

                }else{
                    UsuarioDAO usuarioDAO= new UsuarioDAO(MainActivity.this);
                    String senhaMD5= convertPassMd5(senha.getText().toString().trim());
                    Usuario usuario= usuarioDAO.getUsuario(email.getText().toString().trim(),senhaMD5);


                    if(usuario!=null){
                        SharedPreferences.Editor editor= setting.edit();
                        editor.putString("email",email.getText().toString().trim());
                        editor.putString("senha",senha.getText().toString().trim());
                        editor.commit();
                        Intent intent= new Intent(MainActivity.this, TelaMenu.class);
                        intent.putExtra("usuario",usuario);


                        startActivity(intent);
                        finish();


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
//            textPaint.setColor(Color.BLACK);
            textPaint.setUnderlineText(true);
        }
    }


    class custonOnclickCadastroPlanta extends ClickableSpan {
        @Override
        public void onClick(View texView) {
            Intent intent= new Intent(MainActivity.this,CadastraPlanta.class);
            startActivity(intent);

        }
//
//        public void updateDrawState(TextPaint textPaint) {
//            textPaint.setColor(Color.BLUE);
//            textPaint.setUnderlineText(true);
//        }
    }

    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

}