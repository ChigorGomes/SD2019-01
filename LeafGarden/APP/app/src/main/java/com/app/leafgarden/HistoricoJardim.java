package com.app.leafgarden;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Classe.Historico;
import Classe.ListAdapterHistorico;
import Classe.Usuario;
import DAO.HistoricoDAO;

public class HistoricoJardim extends AppCompatActivity {
    HistoricoDAO historicoDAO;
    ListView listView;
    ArrayList<Historico> historicos;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout );
        listView = findViewById(R.id.listViewPlants);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        try{
            historicoDAO = new HistoricoDAO(this);
            historicos = new ArrayList<>(historicoDAO.getHistorico(usuario));
            ListAdapterHistorico listAdapterHistorico= new ListAdapterHistorico(HistoricoJardim.this,R.layout.activity_meu_jardim,historicos);
            listView.setAdapter(listAdapterHistorico);
        }catch (Exception e){
            Log.e("erro",e.getMessage());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    Historico historico= historicos.get(position);
                    AlertDialog.Builder builder= new AlertDialog.Builder(HistoricoJardim.this);
                    String msg=historico.getInfoLuminosidade()+""+historico.getInfoTemperatura()+""+historico.getInfoUmidade();
                    builder.setMessage(msg);
                    builder.setNeutralButton("Ok",null);
                    builder.show();
                }catch (Exception e){
                    Log.e("erro",e.getMessage());
                }
            }
        });
    }
}
