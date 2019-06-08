package com.app.leafgarden;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import Classe.Planta;

public class InfoPlanta extends AppCompatActivity {
    TextView infoPlanta;
    Planta plantas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_planta);

        infoPlanta = (TextView) findViewById(R.id.textViewTemperaturaPlanta);
        plantas= (Planta) getIntent().getSerializableExtra("planta");
        infoPlanta.setText(plantas.getDescricao());



    }
}
