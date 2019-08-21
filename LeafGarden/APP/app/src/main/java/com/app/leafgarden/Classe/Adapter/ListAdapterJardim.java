package com.app.leafgarden.Classe.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.leafgarden.Classe.Model.Jardim;
import com.app.leafgarden.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapterJardim extends ArrayAdapter<Jardim> {
    private Activity context;
    List<Jardim> jardimList;

    public ListAdapterJardim(Activity context, List<Jardim> jardimList) {
        super(context,  R.layout.activity_plantas, jardimList);
        this.context= context;
        this.jardimList= jardimList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=  context.getLayoutInflater();
        View view= inflater.inflate(R.layout.activity_plantas,null,true);


        ImageView imageView= view.findViewById(R.id.imageViewPlantas);
        TextView textViewNome = view.findViewById(R.id.textViewDicas);

        Jardim jardim= jardimList.get(position);
        Glide.with(context).load(jardim.getImagemUrl()).into(imageView);
        textViewNome.setText(jardim.getNomePlanta());


        return view;



    }
}