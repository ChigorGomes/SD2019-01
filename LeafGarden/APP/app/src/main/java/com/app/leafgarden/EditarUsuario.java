package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Classe.Usuario;
import DAO.UsuarioDAO;


public class EditarUsuario extends AppCompatActivity {
    Usuario usuario;
    TextView nomeUsuario,senhaUsuario, emailUsuario,idadeUsuario;
    Spinner regiao;
    Button buttonEditar;
    int posicao=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_login);
        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        nomeUsuario = findViewById(R.id.editTextNomeEditar);
        emailUsuario = findViewById(R.id.editTextEmailLoginEditar);
        idadeUsuario= findViewById(R.id.editTextIdadeEditar);
        regiao = findViewById(R.id.spinnerRegiaoEditar);
        buttonEditar = findViewById(R.id.buttonEditarUsuario);
        senhaUsuario=  findViewById(R.id.editTextSenhaLoginEditar);

        nomeUsuario.setText(usuario.getNome());
        emailUsuario.setText(usuario.getEmail());
        idadeUsuario.setText(String.valueOf(usuario.getIdade()));
        idadeUsuario.setEnabled(false);
        regiao.setSelection(usuario.getIdRegiao());
        emailUsuario.setEnabled(false);

        regiao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicao= regiao.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nomeUsuario.getText().toString().equals("")){
                    Toast.makeText(EditarUsuario.this,"Preencha o campo nome!",Toast.LENGTH_LONG).show();
                }else if(senhaUsuario.getText().toString().equals("")){
                    Toast.makeText(EditarUsuario.this,"Preencha o campo senha!",Toast.LENGTH_LONG).show();
                }else if(posicao<=0){
                    Toast.makeText(EditarUsuario.this,"Selecione a região!",Toast.LENGTH_LONG).show();

                }else{
                    String senhaMD5= usuario.convertPassMd5(senhaUsuario.getText().toString().trim());
                    usuario.setNome(nomeUsuario.getText().toString());
                    usuario.setSenha(senhaMD5);
                    usuario.setIdRegiao(posicao);
                    UsuarioDAO usuarioDAO= new UsuarioDAO(EditarUsuario.this);
                    if(usuarioDAO.editarUsuario(usuario)){
                        Toast.makeText(EditarUsuario.this,"Editar com sucesso!" +String.valueOf(usuario.getIdUsuario()) ,Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(EditarUsuario.this,TelaMenu.class);
                        intent.putExtra("usuario",usuario);
                        finish();

                        startActivity(intent);
                    }else{
                        Toast.makeText(EditarUsuario.this,"ocorreu um problema!",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

}
