package com.app.leafgarden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import Classe.Planta;
import Classe.Usuario;

public class TelaMenu extends AppCompatActivity {

    private Usuario usuario;
    Button buttonPlanta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);
        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        buttonPlanta = (Button) findViewById(R.id.buttonPlantas);


        buttonPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this,Plantas.class);
                startActivity(intent);
            }
        });


    }
}
