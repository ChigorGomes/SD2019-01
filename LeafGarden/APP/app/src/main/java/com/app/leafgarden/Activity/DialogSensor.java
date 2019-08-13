package com.app.leafgarden.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Timer;
import java.util.TimerTask;

import com.app.leafgarden.Classe.Model.SensorNodeMCU;
import com.app.leafgarden.R;

public class DialogSensor extends AppCompatDialogFragment {
    TextView textViewLuminosidade, textViewTemperaturaAmbiente,textViewTemperaturaSolo,textViewUmidadeAmbiente,textViewUmidadeSolo;
    SensorNodeMCU[] sensor = {null};
    Timer time= new Timer();
    static  final int delay= 100;
    static  final int intervalo= 100;
    static int LOW_LIGHT=2500;
    static  int MEDIUM_LIGHT=10000;
    static int HIGH_LIGHT=20000;
   String auxLuminosidade="";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_sensor,null);
        sensor= (SensorNodeMCU[]) getArguments().getSerializable("sensor");

        textViewLuminosidade = view.findViewById(R.id.textViewEmoticonLuminosidade);
        textViewTemperaturaAmbiente = view.findViewById(R.id.textViewEmoticonTemperaturaAmbiente);
        textViewTemperaturaSolo=view.findViewById(R.id.textViewEmoticonTemperaturaSolo);
        textViewUmidadeAmbiente =view.findViewById(R.id.textViewEmoticonUmidadeAmbiente);
        textViewUmidadeSolo = view.findViewById(R.id.textViewEmoticonUmidadeSolo);



        /*Código que executa a thread*/
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(sensor[0]!=null){
                        if(sensor[0].getLuminosidade() < LOW_LIGHT){
                            auxLuminosidade="BAIXA";


                        }
                        else if(sensor[0].getLuminosidade() >LOW_LIGHT && sensor[0].getLuminosidade() <MEDIUM_LIGHT){
                            auxLuminosidade="MÉDIA";


                        }else if(sensor[0].getLuminosidade() >MEDIUM_LIGHT && sensor[0].getLuminosidade() <HIGH_LIGHT){
                            auxLuminosidade="ALTA";


                        }else{
                            auxLuminosidade="ALTÍSSIMA ";

                        }

//                        textViewLuminosidade.setText(String.valueOf(sensor[0].getLuminosidade()));
                        textViewLuminosidade.setText(auxLuminosidade);
                        textViewTemperaturaAmbiente.setText("Ambiente: "+ String.valueOf(sensor[0].getTemperaturaambiente())+" °C");
                        textViewTemperaturaSolo.setText("Solo: "+String.valueOf(sensor[0].getTemperaturasolo())+" °C");
                        textViewUmidadeAmbiente.setText("Ambiente: "+String.valueOf(sensor[0].getUmidadeambiente())+" %");
                        textViewUmidadeSolo.setText("Solo: "+String.valueOf(sensor[0].getUmidadesolo())+" %");
                    }

                }catch (Exception e){
                    Log.e("erro",e.getMessage());
                }
            }
        },delay,intervalo);

        builder.setView(view);
        return builder.create();
    }



}
