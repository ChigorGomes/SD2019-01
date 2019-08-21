package com.app.leafgarden.Activity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetarSenha extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    EditText editTextEmail;
    Button buttonEnviarEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetar_senha);

        editTextEmail= findViewById(R.id.editTextResetarEmail);
        buttonEnviarEmail = findViewById(R.id.buttonEnviarEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        buttonEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if(email.isEmpty()){
                    editTextEmail.setError("Preencha o campo!");
                    editTextEmail.requestFocus();

                }else if(isValidEmail(email)==false){
                    editTextEmail.setError("formato de e-mail inválido!");
                    editTextEmail.requestFocus();
                }
                else{
                    resetarEmail(email);
                }
            }
        });
    }

    public void resetarEmail(String email){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(ResetarSenha.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetarSenha.this,"Um e-mail foi enviado para você alterar sua senha",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ResetarSenha.this,"E-mail não encontrado!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private  boolean isValidEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){ return true;}
        return  false;
    }
}
