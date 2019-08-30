package com.app.leafgarden.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.Classe.Adapter.ListAdapterDicas;
import com.app.leafgarden.Classe.Model.Jardim;
import com.app.leafgarden.Classe.Model.SensorNodeMCU;
import com.app.leafgarden.FirebaseServicesSensor.Firebase;
import com.app.leafgarden.R;
import com.dm.emotionrating.library.EmotionView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SensorPlanta extends AppCompatActivity {
    Jardim jardim;
    TextView buttonEmoticons;

    ListView listViewDica;
    final SensorNodeMCU[] sensor = {null};
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
    LinearLayout linearConteudo;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    final static String verde= "#12fa35";
    final static String vermelho = "#FA2A2A";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_planta);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        jardim= (Jardim)getIntent().getSerializableExtra("planta");

        listViewDica = findViewById(R.id.listViewDica);
//        TEMPERATURAAMBIENTE = (int)jardim.getTempAmbiente();
//        TEMPERATURASOLO = (int) jardim.getTempSolo();
//        UMIDADEAMBIENTE = (int) jardim.getUmidadeAmbiente();
//        UMIDADESOLO = (int) jardim.getUmidadeSolo();
        buttonEmoticons = findViewById(R.id.textViewPopUpEmoticons);
        SpannableString spannableString =  new SpannableString("");
        spannableString.setSpan(new buttonOnclickEmoticons(),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        buttonEmoticons.setText(spannableString);
        buttonEmoticons.setMovementMethod(LinkMovementMethod.getInstance());
        emotionView= (EmotionView) findViewById(R.id.emotionView);
        view= findViewById(R.id.linearViewEmoticons);
        linearConteudo = (LinearLayout) findViewById(R.id.linearLayoutDicas);




        listaItens = new ArrayList<>();
        adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaItens);

        firebase= new Firebase(SensorPlanta.this);



        firebase.getDatabaseReference().child(jardim.getIdSensor()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inforPlanta[0]=inforPlanta[1]=inforPlanta[2]="";
                infoSensor[0]=infoSensor[1]=infoSensor[2]="";
                sensor[0] = dataSnapshot.getValue(SensorNodeMCU.class);


                listaItens.clear();


                if(sensor[0].getLuminosidade() < LOW_LIGHT){

                    inforPlanta[0] ="Coloque a planta em um local com luminosidade";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(1,2);
                    emotionView.setBackground(ColorDrawable.createFromPath(verde));
                    linearConteudo.setVisibility(View.VISIBLE);



                }if(sensor[0].getLuminosidade()>10){

                    inforPlanta[0] ="Iluminação muito alta\n";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(1,3);
                    emotionView.setBackgroundColor (Color.parseColor(verde));


                }


                ListAdapterDicas listAdapterDicas = new ListAdapterDicas(SensorPlanta.this,R.layout.activity_dicas,listaItens);
                Log.e("msg",String.valueOf(listaItens.size()));
                listViewDica.setAdapter(listAdapterDicas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








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
