package com.app.leafgarden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import Classe.Jardim;
import Classe.SensorNodeMCU;
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
    ListView listViewDica;
    final SensorNodeMCU[] sensor = {null};
    int TEMPERATURASOLO,UMIDADESOLO,UMIDADEAMBIENTE,TEMPERATURAAMBIENTE,LUMINOSIDADE=0;
    private List<String> listaItens;
    private ArrayAdapter<String> adapter;


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
        listViewDica = findViewById(R.id.listViewDica);
        Bitmap bitmap= (Bitmap) BitmapFactory.decodeByteArray(jardim.getFoto(),0,jardim.getFoto().length);
        imageView.setImageBitmap(bitmap);
        TEMPERATURAAMBIENTE = (int)jardim.getTempAmbiente();
        TEMPERATURASOLO = (int) jardim.getTempSolo();
        LUMINOSIDADE = (int) jardim.getLuminosidade();
        UMIDADEAMBIENTE = (int) jardim.getUmidadeAmbiente();
        UMIDADESOLO = (int) jardim.getUmidadeSolo();

        listaItens = new ArrayList<>();
        adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaItens);

        inicializarFirebase();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String msgTemperatura="";
                String msgLuminosidade="";
                String msgUmidade="";
                sensor[0] = dataSnapshot.getValue(SensorNodeMCU.class);
                luminosidade.setText(String.valueOf(sensor[0].getLuminosidade()));
                tempAmbiente.setText(String.valueOf(sensor[0].getTemperaturaambiente()));
                tempSolo.setText(String.valueOf(sensor[0].getTemperaturasolo()));
                umidadeAmbiente.setText(String.valueOf(sensor[0].getUmidadeambiente()));
                umidadeSolo.setText(String.valueOf(sensor[0].getUmidadesolo()));
                listaItens.clear();

                int valorUmidadeSolo= Integer.parseInt(sensor[0].getUmidadesolo().replace("\"","").replace(".",""));
                int valorLuminosidade = Integer.parseInt(sensor[0].getLuminosidade().replace("\"","").replace(".",""));
                int valorTempAmbiente= Integer.parseInt(sensor[0].getTemperaturaambiente().replace("\"","").replace(".",""));
                int valorTempSolo=  Integer.parseInt(sensor[0].getTemperaturasolo().replace("\"","").replace(".",""));
                int valorUmidadeAmbiente = Integer.parseInt(sensor[0].getUmidadeambiente().replace("\"","").replace(".",""));


                if(valorLuminosidade > LUMINOSIDADE){
                    sensorLuminosidade.setBackgroundColor(Color.RED);
                    msgLuminosidade="LUMINOSIDADE:\n Retire a planta do sol!\n";
                    listaItens.add(msgLuminosidade);

                }else{
                    sensorLuminosidade.setBackgroundColor(Color.GREEN);

                }
                if(valorTempAmbiente> TEMPERATURAAMBIENTE || valorTempSolo > TEMPERATURASOLO){
                    sensorTemperatura.setBackgroundColor(Color.RED);
                    msgTemperatura="TEMPERATURA:\n O local que sua planta se encontra está com a temperatura muito acima do que ela suporta!\n";
                    listaItens.add(msgTemperatura);
                }else{
                    sensorTemperatura.setBackgroundColor(Color.GREEN);

                }
                if(valorUmidadeAmbiente> UMIDADEAMBIENTE ||  valorUmidadeSolo >UMIDADESOLO){
                    sensorUmidade.setBackgroundColor(Color.RED);
                    msgUmidade="UMIDADE:\n A umidade está muito alta!\n";
                    listaItens.add(msgUmidade);
                }else{
                    sensorUmidade.setBackgroundColor(Color.GREEN);

                }

                listViewDica.setAdapter(adapter);
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

    private String retornaDadaHora(){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy-HH:mm");
        Date date= new Date();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        Date dataAtual= calendar.getTime();

        String dataCompleta= simpleDateFormat.format(dataAtual);
        return  dataCompleta;
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(SensorPlanta.this);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sensor");
    }




}
