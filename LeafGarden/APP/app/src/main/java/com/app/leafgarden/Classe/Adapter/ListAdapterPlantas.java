package com.app.leafgarden.Classe.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.leafgarden.R;
import com.bumptech.glide.Glide;

import java.util.List;

import com.app.leafgarden.Classe.Model.Planta;

public class ListAdapterPlantas extends ArrayAdapter<Planta> {
    private Activity context;
    List<Planta> plantaList;



    public ListAdapterPlantas(Activity context,  List<Planta> plantaList){
        super(context, R.layout.activity_plantas,plantaList);
        this.context= context;
        this.plantaList= plantaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=  context.getLayoutInflater();
        View view= inflater.inflate(R.layout.activity_plantas,null,true);


        ImageView imageView= view.findViewById(R.id.imageViewPlantas);
        TextView textViewNome = view.findViewById(R.id.textViewDicas);

        Planta planta= plantaList.get(position);
        Glide.with(context).load(planta.getImagemUrl()).into(imageView);
        textViewNome.setText(planta.getNomePlanta());


        return view;



    }
}
