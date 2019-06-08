package com.app.leafgarden;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import Classe.Jardim;
import Classe.ListAdapterJardim;
import Classe.Usuario;
import DAO.JardimDAO;

public class MeuJardim extends AppCompatActivity {

    ListView listView;
    ArrayList<Jardim> jardimArrayList;

    Usuario usuario;
    JardimDAO jardimDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout);
        listView = findViewById(R.id.listViewPlants);
        usuario= (Usuario)getIntent().getSerializableExtra("usuario");

        try{
            jardimDAO=  new JardimDAO(this);
            jardimArrayList = new ArrayList<>(jardimDAO.getJardim(usuario));
            ListAdapterJardim listAdapterJardim= new ListAdapterJardim(MeuJardim.this,R.layout.activity_plantas,jardimArrayList);
            listView.setAdapter(listAdapterJardim);

        }catch (Exception e){
            Log.e("erro",e.getMessage());
        }


    }
}
