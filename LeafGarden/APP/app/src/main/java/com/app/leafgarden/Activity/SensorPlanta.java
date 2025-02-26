package com.app.leafgarden.Activity;

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

import java.util.ArrayList;
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
    EmotionView emotionView= null;
    View view;
    LinearLayout linearConteudo;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    final static String vermelho = "#FF6347";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_planta);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        jardim= (Jardim)getIntent().getSerializableExtra("planta");
        listViewDica = findViewById(R.id.listViewDica);
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
                emotionView.setRating(2,4);
                listaItens.clear();
                if(sensor[0].getLuminosidade() <  2500 ){
                    inforPlanta[0] ="Luminosidade muito baixa";
                    infoSensor[0]= String.valueOf(sensor[0].getLuminosidade()+"%\n");
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(4,2);
                    emotionView.setBackground(ColorDrawable.createFromPath(vermelho));
                    linearConteudo.setVisibility(View.VISIBLE);
                }if(sensor[0].getLuminosidade() >10000){
                    inforPlanta[0] ="Luminosidade muito alta";
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(4,2);
                    emotionView.setBackground(ColorDrawable.createFromPath(vermelho));
                    linearConteudo.setVisibility(View.VISIBLE);
                }if(sensor[0].getUmidadeambiente() < 30 || sensor[0].getUmidadesolo() < 30){
                    inforPlanta[0] ="Umidade baixa  desacelera o crescimento das plantas.";
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(4,2);
                    emotionView.setBackground(ColorDrawable.createFromPath(vermelho));
                    linearConteudo.setVisibility(View.VISIBLE);
                }if(sensor[0].getUmidadeambiente() >70 || sensor[0].getUmidadesolo() > 70 ){
                    inforPlanta[0] ="Umidade alta é favorável ao crescimento dos fungos.";
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(4,2);
                    emotionView.setBackground(ColorDrawable.createFromPath(vermelho));
                    linearConteudo.setVisibility(View.VISIBLE);
                }if(sensor[0].getTemperaturaambiente() > jardim.getTempAmbiente()+2 || sensor[0].getTemperaturasolo() > jardim.getTempSolo() + 2){
                    inforPlanta[0] ="Temperatura alta reduz o crescimento das plantas.";
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(4,2);
                    emotionView.setBackground(ColorDrawable.createFromPath(vermelho));
                    linearConteudo.setVisibility(View.VISIBLE);
                }if(sensor[0].getTemperaturaambiente() < jardim.getTempAmbiente()-2 || sensor[0].getTemperaturasolo() < jardim.getTempSolo() - 2){
                    inforPlanta[0] ="Temperatura baixa limita o crescimento das plantas.";
                    listaItens.add(inforPlanta[0]);
                    emotionView.setRating(4,2);
                    emotionView.setBackground(ColorDrawable.createFromPath(vermelho));
                    linearConteudo.setVisibility(View.VISIBLE);

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
