package com.app.leafgarden.Classe.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.leafgarden.Classe.Model.Historico;
import com.app.leafgarden.R;

import java.util.List;

public class ListAdapterHistorico extends ArrayAdapter<Historico> {
    private Activity context;
    List<Historico> historicoList;

    public ListAdapterHistorico(Activity context, List<Historico> historicoList) {
        super(context, R.layout.activity_dicas, historicoList);
        this.context = context;
        this.historicoList = historicoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater=  context.getLayoutInflater();
        View view= inflater.inflate(R.layout.activity_dicas,null,true);


        TextView textViewNome = view.findViewById(R.id.textViewDicas);

        Historico historico= historicoList.get(position);

        textViewNome.setText(historico.getHorario());


        return view;



    }



}


