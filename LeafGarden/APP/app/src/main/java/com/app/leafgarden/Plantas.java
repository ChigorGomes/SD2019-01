package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Classe.ListAdapterPlantas;
import Classe.Planta;
import Classe.Usuario;
import DAO.PlantaDAO;

public class Plantas extends AppCompatActivity{

   PlantaDAO plantaDAO;
    ListView listView;
    ArrayList<Planta> plantaVector;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout);
        listView= findViewById(R.id.listViewPlants);

        usuario= (Usuario) getIntent().getSerializableExtra("usuario");

        try{

            plantaDAO= new PlantaDAO(this);
            plantaVector= new ArrayList<>(plantaDAO.getPlanta());

            ListAdapterPlantas listAdapterPlantas= new ListAdapterPlantas(Plantas.this,R.layout.activity_plantas,plantaVector);
            listView.setAdapter(listAdapterPlantas);

        }catch (Exception e){
            Log.e("erro",e.getMessage());
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    Intent intent= new Intent(Plantas.this,InfoPlanta.class);
                    intent.putExtra("usuario",usuario);
                    intent.putExtra("planta",plantaVector.get(position));
                    startActivity(intent);
                }catch (Exception e){
                    Log.e("erro",e.getMessage());
                }

            }
        });
    }



}
