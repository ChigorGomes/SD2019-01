package com.app.leafgarden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.leafgarden.FirebaseServices.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Classe.Historico;
import Classe.Jardim;
import Classe.SensorNodeMCU;
import Classe.Usuario;
import DAO.HistoricoDAO;

public class SensorPlanta extends AppCompatActivity {
    Usuario usuario;
    Jardim jardim;
    Historico historico;
    HistoricoDAO historicoDAO;
    TextView nomePlanta,tempAmbiente, luminosidade,tempSolo,umidadeAmbiente,umidadeSolo;
    ImageView imageView;
    ImageView sensorUmidade,sensorTemperatura,sensorLuminosidade;
    ListView listViewDica;
    final SensorNodeMCU[] sensor = {null};
    int TEMPERATURASOLO,UMIDADESOLO,UMIDADEAMBIENTE,TEMPERATURAAMBIENTE;
    private List<String> listaItens;
    private ArrayAdapter<String> adapter;
    final String[] inforPlanta = {"","",""};
    final String[] infoSensor= {"","",""};
    Firebase firebase;
    final static int LOW_LIGHT=2500;
    final static  int MEDIUM_LIGHT=10000;
    final static int HIGH_LIGHT=20000;
    static  final int delay= 10000;
    static  final int intervalo= 100000;
    Timer time= new Timer();


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
        UMIDADEAMBIENTE = (int) jardim.getUmidadeAmbiente();
        UMIDADESOLO = (int) jardim.getUmidadeSolo();

        sensorLuminosidade.setBackground(Drawable.createFromPath(""));
        sensorUmidade.setBackground(Drawable.createFromPath(""));
        sensorTemperatura.setBackground(Drawable.createFromPath(""));

        listaItens = new ArrayList<>();
        adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaItens);

        firebase= new Firebase(SensorPlanta.this);


        firebase.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inforPlanta[0]=inforPlanta[1]=inforPlanta[2]="";
                infoSensor[0]=infoSensor[1]=infoSensor[2]="";
                sensor[0] = dataSnapshot.getValue(SensorNodeMCU.class);
                tempAmbiente.setText(String.valueOf(sensor[0].getTemperaturaambiente())+"°C A");
                tempSolo.setText(String.valueOf(sensor[0].getTemperaturasolo())+"°C S");
                umidadeAmbiente.setText(String.valueOf(sensor[0].getUmidadeambiente()+"% A"));
                umidadeSolo.setText(String.valueOf(sensor[0].getUmidadesolo())+"% S");
                listaItens.clear();


                if(sensor[0].getLuminosidade() < LOW_LIGHT){
                    luminosidade.setText("BAIXA");
                    luminosidade.setTextColor(Color.RED);
                    inforPlanta[0] ="LUMINOSIDADE:\n coloque a planta em um local com luminosidade\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);

                }else if(sensor[0].getLuminosidade() >LOW_LIGHT && sensor[0].getLuminosidade() <MEDIUM_LIGHT){
                    luminosidade.setText("MÉDIA");
                    luminosidade.setTextColor(Color.YELLOW);
                    inforPlanta[0] ="LUMINOSIDADE:\n está com a iluminação razoável\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                }else if(sensor[0].getLuminosidade() >MEDIUM_LIGHT && sensor[0].getLuminosidade() <HIGH_LIGHT){
                    luminosidade.setText("BOA");
                    luminosidade.setTextColor(Color.parseColor("#008000"));
                    inforPlanta[0] ="LUMINOSIDADE:\n está com boa iluminação\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                }else{
                    luminosidade.setText("ALTA");
                    luminosidade.setTextColor(Color.parseColor("#d11507"));
                    inforPlanta[0] ="LUMINOSIDADE:\n a planta está recebendo muita iluminação\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                }
                if(sensor[0].getTemperaturaambiente()> TEMPERATURAAMBIENTE){
                    tempAmbiente.setTextColor(Color.RED);
                    inforPlanta[1] ="TEMPERATURA:\n O local que sua planta se encontra está com a temperatura muito acima do que ela suporta!\n";
                    infoSensor[1]= String.valueOf("A: "+sensor[0].getTemperaturaambiente()+"|S: "+sensor[0].getTemperaturasolo()+"\n");
                    listaItens.add(inforPlanta[1]);
                }else{
                    tempAmbiente.setTextColor(Color.parseColor("#008000"));

                }
                if (sensor[0].temperaturasolo > TEMPERATURASOLO){
                    tempSolo.setTextColor(Color.RED);
                }else{
                    tempSolo.setTextColor(Color.parseColor("#008000"));

                }
                if(sensor[0].getUmidadeambiente()> UMIDADEAMBIENTE){
                    umidadeAmbiente.setTextColor(Color.RED);
                    inforPlanta[2] ="UMIDADE:\n A umidade está muito alta!\n";
                    infoSensor[2]= String.valueOf("A: "+sensor[0].getUmidadeambiente()+"|S: "+sensor[0].getUmidadesolo()+"\n");
                    listaItens.add(inforPlanta[2]);
                }else{
                    umidadeAmbiente.setTextColor(Color.parseColor("#008000"));

                }
                if( sensor[0].getUmidadesolo() >UMIDADESOLO){
                    umidadeSolo.setTextColor(Color.RED);
                }else{
                    umidadeSolo.setTextColor(Color.parseColor("#008000"));

                }


                listViewDica.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*Código que executa a thread*/
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if((! inforPlanta[0].equals(""))||(!inforPlanta[1].equals("")) || (!inforPlanta[2].equals("")) ){
                        String data= retornaDadaHora();

                        historico= new Historico(data,inforPlanta[0]+infoSensor[0],inforPlanta[1]+infoSensor[1],inforPlanta[2]+infoSensor[2]);
                        historicoDAO= new HistoricoDAO(SensorPlanta.this);
                        if(historicoDAO.addHistorico(historico,jardim)){
                            Log.e("erro","salvado com sucesso!");
                        }else{
                            Log.e("erro","ocorreu um erro");
                        }

                    }

                }catch (Exception e){
                    Log.e("erro",e.getMessage());
                }
            }
        },delay,intervalo);


    }
    private String retornaDadaHora(){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date date= new Date();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        Date dataAtual= calendar.getTime();

        String dataCompleta= simpleDateFormat.format(dataAtual);
        return  dataCompleta;
    }



}
