package com.app.leafgarden;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import Classe.Usuario;


public class EditarUsuario extends AppCompatActivity {
    Usuario usuario;
    TextView nomeUsuario,senhaUsuario, emailUsuario,idadeUsuario;
    Spinner regiao;
    Button buttonEditar;
    int posicao=0;
    FirebaseAuth  firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_login);

        nomeUsuario = findViewById(R.id.editTextNomeEditar);
        emailUsuario = findViewById(R.id.editTextEmailLoginEditar);
        idadeUsuario = findViewById(R.id.editTextIdadeEditar);
        regiao = findViewById(R.id.spinnerRegiaoEditar);
        buttonEditar = findViewById(R.id.buttonEditarUsuario);
        senhaUsuario = findViewById(R.id.editTextSenhaLoginEditar);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        nomeUsuario.setText(usuario.getNome());
        emailUsuario.setText(usuario.getEmail());
        idadeUsuario.setText(String.valueOf(usuario.getIdade()));
        idadeUsuario.setEnabled(false);
//        regiao.setSelection(usuario.getIdRegiao());
        emailUsuario.setEnabled(false);





        regiao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicao = regiao.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
