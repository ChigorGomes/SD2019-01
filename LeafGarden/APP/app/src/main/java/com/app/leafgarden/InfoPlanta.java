package com.app.leafgarden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Classe.Planta;

public class InfoPlanta extends AppCompatActivity {
    TextView nomePlanta,descricaoPlanta,localAdequado,temperaturasIdeais;
    ImageView imageView;
    Planta planta;
    Button buttonCadastroJardim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_planta);

        planta= (Planta) getIntent().getSerializableExtra("planta");
        nomePlanta =  findViewById(R.id.textViewNomeInfoPlanta);
        descricaoPlanta = findViewById(R.id.textViewDescricaoPlanta);
        localAdequado = findViewById(R.id.textViewLocalAdequadoInfoPlant);
        temperaturasIdeais = findViewById(R.id.textViewTemperaturaInfoPlanta);
        imageView = findViewById(R.id.imageViewInfoPlanta);
        buttonCadastroJardim = findViewById(R.id.buttonAdcPlantaNoJardim);

        nomePlanta.setText(planta.getNomePlanta());
        descricaoPlanta.setText(planta.getDescricao());
        localAdequado.setText(planta.getLocalAdequado());
        Bitmap bitmap= (Bitmap) BitmapFactory.decodeByteArray(planta.getFoto(),0,planta.getFoto().length);
        String temp="Umidade: "+"Solo: "+planta.getUmidadeSolo()+" | Ambiente: "+planta.getUmidadeAmbiente()+"\n"+
                "Temperatura: "+"Solo: " +planta.getTempSolo()+" |Ambiente: "+planta.getTempAmbiente();
        temperaturasIdeais.setText(temp);

        buttonCadastroJardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
