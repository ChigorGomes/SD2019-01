package com.app.leafgarden;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Classe.Jardim;
import Classe.Usuario;

public class MeuJardim extends AppCompatActivity {

    ListView listView;
    ArrayList<Jardim> jardimArrayList;

    Usuario usuario;
//    JardimDAO jardimDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout);
        listView = findViewById(R.id.listViewPlants);
        usuario= (Usuario)getIntent().getSerializableExtra("usuario");
//
//        try{
//            jardimDAO=  new JardimDAO(this);
//            jardimArrayList = new ArrayList<>(jardimDAO.getJardim(usuario));
//            ListAdapterJardim listAdapterJardim= new ListAdapterJardim(MeuJardim.this,R.layout.activity_plantas,jardimArrayList);
//            listView.setAdapter(listAdapterJardim);
//
//        }catch (Exception e){
//            Log.e("erro",e.getMessage());
//        }
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                try{
//                    Intent intent= new Intent(MeuJardim.this,SensorPlanta.class);
//                    intent.putExtra("usuario",usuario);
//                    intent.putExtra("jardim",jardimArrayList.get(position));
//                    startActivity(intent);
//                }catch (Exception e){
//                    Log.e("erro",e.getMessage());
//                }
//
//            }
//        });


    }

}
