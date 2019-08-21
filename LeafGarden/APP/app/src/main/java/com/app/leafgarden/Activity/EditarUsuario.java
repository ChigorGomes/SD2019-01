package com.app.leafgarden.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.Classe.Model.Usuario;
import com.app.leafgarden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditarUsuario extends AppCompatActivity {
    TextView  textViewnomeUsuario;
//    TextView    textViewsenhaUsuario;
    TextView    textViewemailUsuario;
    TextView     textViewidadeUsuario;
    Spinner regiao;
    Button buttonEditar;
    int posicao=0;
    FirebaseAuth  firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseDatabase databaseReference;
    Usuario usuarioAux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_login);

        textViewnomeUsuario = findViewById(R.id.editTextNomeEditar);
        textViewemailUsuario = findViewById(R.id.editTextEmailLoginEditar);
        textViewidadeUsuario = findViewById(R.id.editTextIdadeEditar);
        regiao = findViewById(R.id.spinnerRegiaoEditar);
        buttonEditar = findViewById(R.id.buttonEditarUsuario);
//        textViewsenhaUsuario = findViewById(R.id.editTextSenhaLoginEditar);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance();
        textViewemailUsuario.setEnabled(false);
//        textViewsenhaUsuario.setEnabled(false);



        databaseReference.getReference().child("Usuario/"+firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                textViewnomeUsuario.setText(usuario.getNome());
                textViewemailUsuario.setText(usuario.getEmail());
                textViewidadeUsuario.setText(usuario.getIdade());
                regiao.setSelection(Integer.parseInt(usuario.getRegiao()));
                usuarioAux= usuario;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        regiao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicao = regiao.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaUsuario();
            }
        });

    }


    public void verificaUsuario(){
        String nome =  textViewnomeUsuario.getText().toString();
        String idade= textViewidadeUsuario.getText().toString().trim();
        String regiaoStr = String.valueOf(posicao);


        if(nome.isEmpty()){
            textViewnomeUsuario.setError("Preencha o campo nome");
            textViewnomeUsuario.requestFocus();
        }else if(idade.isEmpty()){
            textViewidadeUsuario.setError("Preencha o campo nome");
            textViewidadeUsuario.requestFocus();
        }else if(posicao <=0){
            Toast.makeText(EditarUsuario.this,"Selecione a região",Toast.LENGTH_SHORT).show();
            regiao.requestFocus();
        }


        else{
            Usuario user= new Usuario();

            user.setEmail(usuarioAux.getEmail());

            user.setIdade(idade);
            user.setRegiao(regiaoStr);
            user.setNome(nome);



            databaseReference.getReference().child("Usuario").child(firebaseAuth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditarUsuario.this,"Editado com sucesso",Toast.LENGTH_SHORT).show();
                    Intent  intent= new Intent(EditarUsuario.this,TelaMenu.class);
                    startActivity(intent);
                }
            });

        }


    }


}
