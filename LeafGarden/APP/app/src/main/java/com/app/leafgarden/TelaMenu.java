package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import Classe.Usuario;

public class TelaMenu extends AppCompatActivity {
    private Usuario usuario;
    Button buttonPlanta, buttomMeuJardim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);
        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        buttonPlanta = (Button) findViewById(R.id.buttonPlantas);
        buttomMeuJardim= findViewById(R.id.buttonMeuJardim);

        buttonPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this,Plantas.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });

        buttomMeuJardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this, MeuJardim.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);

            }
        });


    }
}
