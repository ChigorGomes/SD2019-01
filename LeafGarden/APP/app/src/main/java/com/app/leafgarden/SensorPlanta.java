package com.app.leafgarden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import Classe.Jardim;
import Classe.Usuario;

public class SensorPlanta extends AppCompatActivity {
    Usuario usuario;
    Jardim jardim;
    TextView nomePlanta,tempAmbiente, luminosidade,tempSolo,umidadeAmbiente,umidadeSolo;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_planta);
        usuario= (Usuario)getIntent().getSerializableExtra("usuario");
        jardim= (Jardim)getIntent().getSerializableExtra("jardim");
        nomePlanta = findViewById(R.id.textViewNomePlantaSensor);
        tempAmbiente = findViewById(R.id.textViewSensorTempAmbiente);
        tempSolo= findViewById(R.id.textViewSensorTempSolo);
        umidadeAmbiente = findViewById(R.id.textViewSensorUmidadeAmbiente);
        umidadeSolo= findViewById(R.id.textViewSensorUmidadeSolo);
        luminosidade = findViewById(R.id.textViewSensorLuminosidade);
        imageView= findViewById(R.id.imageViewSensorImg);
        nomePlanta.setText(jardim.getNomePlanta());
        Bitmap bitmap= (Bitmap) BitmapFactory.decodeByteArray(jardim.getFoto(),0,jardim.getFoto().length);
        imageView.setImageBitmap(bitmap);

    }
}
