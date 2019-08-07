package com.app.leafgarden;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.FirebaseServices.Firebase;
import com.dm.emotionrating.library.EmotionView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    TextView buttonEmoticons;

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
    EmotionView emotionView= null;
    View view;
    ImageButton dicasInfo;
    LinearLayout linearConteudo;
    boolean show= false;


    static final int PESOMENOR=5;
    static final int PESOMAIOR=10;
//    Timer time= new Timer();
//    static  final int delay= 10000;
//    static  final int intervalo= 100000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_planta);
        usuario= (Usuario)getIntent().getSerializableExtra("usuario");
        jardim= (Jardim)getIntent().getSerializableExtra("jardim");

        listViewDica = findViewById(R.id.listViewDica);
        TEMPERATURAAMBIENTE = (int)jardim.getTempAmbiente();
        TEMPERATURASOLO = (int) jardim.getTempSolo();
        UMIDADEAMBIENTE = (int) jardim.getUmidadeAmbiente();
        UMIDADESOLO = (int) jardim.getUmidadeSolo();
        buttonEmoticons = findViewById(R.id.textViewPopUpEmoticons);
        SpannableString spannableString =  new SpannableString("");
        spannableString.setSpan(new buttonOnclickEmoticons(),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        buttonEmoticons.setText(spannableString);
        buttonEmoticons.setMovementMethod(LinkMovementMethod.getInstance());
        emotionView= (EmotionView) findViewById(R.id.emotionView);
        view= findViewById(R.id.linearViewEmoticons);
        dicasInfo= findViewById(R.id.imageButtonInfoDicas);
        linearConteudo = (LinearLayout) findViewById(R.id.linearLayoutDicas);




        listaItens = new ArrayList<>();
        adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaItens);

        firebase= new Firebase(SensorPlanta.this);


        dicasInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show){
                    linearConteudo.setVisibility(View.INVISIBLE);
                    show =false;
                }else{
                    linearConteudo.setVisibility(View.VISIBLE);
                    show =true;
                }
            }
        });


        firebase.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inforPlanta[0]=inforPlanta[1]=inforPlanta[2]="";
                infoSensor[0]=infoSensor[1]=infoSensor[2]="";
                sensor[0] = dataSnapshot.getValue(SensorNodeMCU.class);


                listaItens.clear();


                if(sensor[0].getLuminosidade() < LOW_LIGHT){

                    inforPlanta[0] ="LUMINOSIDADE:\n coloque a planta em um local com luminosidade\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(1,2);
                    emotionView.setBackgroundColor(Color.parseColor("#ee3a1f"));
                    view.setBackgroundColor(Color.parseColor("#ee3a1f"));
                    listViewDica.setBackgroundColor(Color.parseColor("#ee3a1f"));
                    dicasInfo.setBackgroundColor(Color.parseColor("#ee3a1f"));
                    dicasInfo.setVisibility(View.VISIBLE);


                }
                else if(sensor[0].getLuminosidade() >LOW_LIGHT && sensor[0].getLuminosidade() <MEDIUM_LIGHT){

                    inforPlanta[0] ="LUMINOSIDADE:\n está com a iluminação razoável\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(1,3);
                    emotionView.setBackgroundColor(Color.GREEN);
                    view.setBackgroundColor(Color.GREEN);
                    dicasInfo.setVisibility(View.INVISIBLE);


                }else if(sensor[0].getLuminosidade() >MEDIUM_LIGHT && sensor[0].getLuminosidade() <HIGH_LIGHT){

                    inforPlanta[0] ="LUMINOSIDADE:\n está com boa iluminação\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                }else{

                    inforPlanta[0] ="LUMINOSIDADE:\n a planta está recebendo muita iluminação\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
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
//                try {
//                    if((! inforPlanta[0].equals(""))||(!inforPlanta[1].equals("")) || (!inforPlanta[2].equals("")) ){
//                        String data= retornaDadaHora();
//
//                        historico= new Historico(data,inforPlanta[0]+infoSensor[0],inforPlanta[1]+infoSensor[1],inforPlanta[2]+infoSensor[2]);
//                        historicoDAO= new HistoricoDAO(SensorPlanta.this);
//                        if(historicoDAO.addHistorico(historico,jardim)){
//                            Log.e("erro","salvado com sucesso!");
//                        }else{
//                            Log.e("erro","ocorreu um erro");
//                        }
//
//                    }
//
//                }catch (Exception e){
//                    Log.e("erro",e.getMessage());
//                }
//            }
//        },delay,intervalo);


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

    class buttonOnclickEmoticons extends ClickableSpan{

        @Override
        public void onClick(@NonNull View widget) {
            openDialog();
        }
    }

    public void openDialog(){
        DialogSensor dialogSensor= new DialogSensor();
        Bundle bundle= new Bundle();
        bundle.putSerializable("sensor",sensor);
        dialogSensor.setArguments(bundle);
        dialogSensor.show(getSupportFragmentManager(),"");
    }



}
