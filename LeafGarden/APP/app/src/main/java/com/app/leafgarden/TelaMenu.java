package com.app.leafgarden;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import Classe.Usuario;

public class TelaMenu extends AppCompatActivity {

    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);
        usuario= (Usuario) getIntent().getSerializableExtra("usuario");

        Toast.makeText(TelaMenu.this,usuario.getNome(),Toast.LENGTH_SHORT).show();

    }
}
