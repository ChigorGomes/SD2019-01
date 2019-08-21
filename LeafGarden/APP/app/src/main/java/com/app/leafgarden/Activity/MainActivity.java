package com.app.leafgarden.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button buttonCadastroPlanta;
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
        buttonCadastroPlanta = findViewById(R.id.textViewCadastroPlanta);
        buttonNovaConta =  findViewById(R.id.textViewNovaConta);
        editTextemail = (EditText) findViewById(R.id.editTextEmailLoginEditar);
        editTextsenha = (EditText) findViewById(R.id.editTextSenhaLoginEditar);
        buttonEntrar= (Button) findViewById(R.id.buttonEntrar);
        setting =   getSharedPreferences(PREFS_NAME,0);

        editTextemail.setText(setting.getString("email",""));
        editTextsenha.setText(setting.getString("senha",""));


        firebaseAuth = FirebaseAuth.getInstance();


        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaUsuario();

            }
        });

        buttonCadastroPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,CadastraPlanta.class);
                startActivity(intent);
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
            editTextemail.setError("Preencha o campo Email!");
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
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }




}