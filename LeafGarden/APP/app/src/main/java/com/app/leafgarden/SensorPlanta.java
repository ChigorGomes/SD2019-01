package com.app.leafgarden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;

import Classe.Jardim;
import Classe.Sensor;
import Classe.Usuario;

public class SensorPlanta extends AppCompatActivity {
    Usuario usuario;
    Jardim jardim;
    TextView nomePlanta,tempAmbiente, luminosidade,tempSolo,umidadeAmbiente,umidadeSolo;
    ImageView imageView;
    int delay= 0;
    int interval= 1000;
    Timer time= new Timer();
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ImageView sensorUmidade,sensorTemperatura,sensorLuminosidade;
    final Sensor[] sensor = {null};
    int TEMPERATURASOLO,UMIDADESOLO,UMIDADEAMBIENTE,TEMPERATURAAMBIENTE,LUMINOSIDADE=0;


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
        sensorUmidade = findViewById(R.id.imageViewSensorUmidade);
        sensorTemperatura = findViewById(R.id.imageViewSensorTemperatura);
        sensorLuminosidade=  findViewById(R.id.imageViewSensorLuminosidade);
        nomePlanta.setText(jardim.getNomePlanta());
        Bitmap bitmap= (Bitmap) BitmapFactory.decodeByteArray(jardim.getFoto(),0,jardim.getFoto().length);
        imageView.setImageBitmap(bitmap);

        TEMPERATURAAMBIENTE = (int)jardim.getTempAmbiente();
        TEMPERATURASOLO = (int) jardim.getTempSolo();
        LUMINOSIDADE = (int) jardim.getLuminosidade();
        UMIDADEAMBIENTE = (int) jardim.getUmidadeAmbiente();
        UMIDADESOLO = (int) jardim.getUmidadeSolo();

        inicializarFirebase();





    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            sensor[0] = dataSnapshot.getValue(Sensor.class);
            luminosidade.setText(String.valueOf(sensor[0].getLuminosidade()));
            tempAmbiente.setText(String.valueOf(sensor[0].getTemperatura()));
            umidadeAmbiente.setText(String.valueOf(sensor[0].getUmidade()));


            if(sensor[0].getTemperatura()> TEMPERATURAAMBIENTE){
//                luminosidade.setText(String.valueOf(sensor[0].getTemperatura()));
//                luminosidade.setTextColor(Color.BLUE);
                    sensorTemperatura.setBackgroundColor(Color.RED);

            }else sensorTemperatura.setBackgroundColor(Color.GREEN);
            if(sensor[0].getLuminosidade()> LUMINOSIDADE){
                sensorLuminosidade.setBackgroundColor(Color.RED);

            }else sensorLuminosidade.setBackgroundColor(Color.GREEN);
            if(sensor[0].getUmidade()> UMIDADESOLO){
//                luminosidade.setText(String.valueOf(sensor[0].getTemperatura()));
//                luminosidade.setTextColor(Color.BLUE);
                sensorUmidade.setBackgroundColor(Color.RED);

            }else sensorUmidade.setBackgroundColor(Color.GREEN);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });




        /*Código que executa a thread*/
//        time.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if(sensor[0].getTemperatura()>10){
//                    luminosidade.setText(String.valueOf(sensor[0].getTemperatura()));
//                    luminosidade.setTextColor(Color.BLUE);
//
//                }
//            }
//        },delay,interval);
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(SensorPlanta.this);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sensor");
    }




}
