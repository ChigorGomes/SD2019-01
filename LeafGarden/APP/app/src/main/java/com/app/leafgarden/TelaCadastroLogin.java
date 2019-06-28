package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Classe.Usuario;
import DAO.UsuarioDAO;

public class TelaCadastroLogin extends AppCompatActivity {

    Usuario usuario;
    EditText nome;
    EditText senha;
    EditText idade;
    EditText email;
    Button cadastrar;
    Spinner spinner;
    int posicao=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_login);
        nome = (EditText) findViewById(R.id.editTextNomeEditar);
        senha = (EditText) findViewById(R.id.editTextSenhaLoginEditar);
        idade = (EditText) findViewById(R.id.editTextIdadeEditar);
        email = (EditText) findViewById(R.id.editTextEmailLoginEditar);
        cadastrar = (Button) findViewById(R.id.buttonEditarUsuario);
        spinner = (Spinner)  findViewById(R.id.spinnerRegiaoEditar);



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
                if(nome.getText().toString().equals("")){
                    Toast.makeText(TelaCadastroLogin.this,"Preencha o campo nome!",Toast.LENGTH_SHORT).show();
                }else if(senha.getText().toString().equals("")){
                    Toast.makeText(TelaCadastroLogin.this,"Preencha o campo senha!",Toast.LENGTH_SHORT).show();

                }else if(idade.getText().toString().equals("")){
                    Toast.makeText(TelaCadastroLogin.this,"Preencha o campo idade!",Toast.LENGTH_SHORT).show();

                }else if(email.getText().toString().equals("")){
                    Toast.makeText(TelaCadastroLogin.this,"Preencha o campo email!",Toast.LENGTH_SHORT).show();

                }else if(isValidEmail(email.getText().toString().trim())==false){
                    Toast.makeText(TelaCadastroLogin.this,"Formato inválido de e-mail!",Toast.LENGTH_SHORT).show();

                }
                else if(posicao <=0){
                    Toast.makeText(TelaCadastroLogin.this,"Selecione a região!",Toast.LENGTH_SHORT).show();

                }
                else{
                    String senhaMD5= usuario.convertPassMd5(senha.getText().toString().trim());
                    Log.e("erro",senhaMD5);
                    int year= Integer.parseInt(idade.getText().toString());
                    usuario= new Usuario(nome.getText().toString(),email.getText().toString().trim(),senhaMD5,year,posicao);
                    UsuarioDAO usuarioDAO= new UsuarioDAO(TelaCadastroLogin.this);
                    if(usuarioDAO.addUsuario(usuario)){
                        Toast.makeText(TelaCadastroLogin.this,"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(TelaCadastroLogin.this,TelaMenu.class);

                        usuario=usuarioDAO.getUsuario(email.getText().toString().trim(),senhaMD5);
                        intent.putExtra("usuario",usuario);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(TelaCadastroLogin.this,"Email já cadastrado!",Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });


    }

    private  boolean isValidEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){ return true;}
         return  false;
    }
}
