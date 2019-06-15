package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Classe.Usuario;
import DAO.UsuarioDAO;

public class TelaCadastroLogin extends AppCompatActivity {

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

                }else if(posicao <=0){
                    Toast.makeText(TelaCadastroLogin.this,"Selecione a região!",Toast.LENGTH_SHORT).show();

                }
                else{
                    int idad= Integer.parseInt(idade.getText().toString());
                    String senhaMD5= convertPassMd5(senha.getText().toString().trim());
                    Usuario usu= new Usuario(nome.getText().toString(),email.getText().toString().trim(),senhaMD5,idad,posicao);
                    UsuarioDAO usuarioDAO= new UsuarioDAO(TelaCadastroLogin.this);

                    if(usuarioDAO.addUsuario(usu)){
                        Toast.makeText(TelaCadastroLogin.this,"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(TelaCadastroLogin.this,TelaMenu.class);

                        usu=usuarioDAO.getUsuario(email.getText().toString().trim(),senhaMD5);
                        intent.putExtra("usuario",usu);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(TelaCadastroLogin.this,"Email já cadastrado!",Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });


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
