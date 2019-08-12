package com.app.leafgarden;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Classe.Usuario;

public class TelaCadastroLogin extends AppCompatActivity  {



    EditText editTextnome;
    EditText editTextsenha;
    EditText editTextidade;
    EditText editTextemail;
    Button cadastrar;
    Spinner spinner;
    int posicao=0;
     FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_login);
        editTextnome = (EditText) findViewById(R.id.editTextNomeEditar);
        editTextsenha = (EditText) findViewById(R.id.editTextSenhaLoginEditar);
        editTextidade = (EditText) findViewById(R.id.editTextIdadeEditar);
        editTextemail = (EditText) findViewById(R.id.editTextEmailLoginEditar);
        cadastrar = (Button) findViewById(R.id.buttonEditarUsuario);
        spinner = (Spinner)  findViewById(R.id.spinnerRegiaoEditar);

        firebaseAuth = FirebaseAuth.getInstance();






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicao= spinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaUsuario();


            }
        });


    }




    private void verificaUsuario(){
         final String nome= editTextnome.getText().toString();
         final String email = editTextemail.getText().toString().trim();
         final String senha = editTextsenha.getText().toString().trim();
         final String idade= editTextidade.getText().toString();
         final String pos = String.valueOf(posicao);



        if(nome.isEmpty()){
            editTextnome.setError("Preencha o campo nome!");
            editTextnome.requestFocus();

        } if(email.isEmpty()){
            editTextemail.setError("Preencha o campo email!");
            editTextemail.requestFocus();
            if(isValidEmail(email)==false){
                editTextemail.setError("verifique o email!");
                editTextemail.requestFocus();
            }
        }  if(senha.isEmpty()){
            editTextsenha.setError("Preencha o campo senha");
            editTextsenha.requestFocus();

        }  if(idade.isEmpty()){
            editTextidade.setError("Preencha o campo idade");
            editTextidade.requestFocus();

        } if(nome.isEmpty()){
            editTextnome.setError("Preencha o campo nome");
            editTextnome.requestFocus();
            return;
        } if(posicao <=0){
            Toast.makeText(TelaCadastroLogin.this,"Selecione a região!",Toast.LENGTH_SHORT).show();
            spinner.requestFocus();

        }
            firebaseAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(TelaCadastroLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Usuario usuario= new Usuario(nome,email,idade,pos);

                                FirebaseDatabase.getInstance().getReference().child("Usuario")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(TelaCadastroLogin.this,"Deu certo",Toast.LENGTH_SHORT).show();


                                    }
                                });

                            } else {
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(TelaCadastroLogin.this,"Email já cadastrado",Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(TelaCadastroLogin.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                }

                            }

                        }
                    });



    }

    private  boolean isValidEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){ return true;}
         return  false;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
    }

}
