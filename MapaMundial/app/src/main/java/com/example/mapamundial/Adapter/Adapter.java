package com.example.mapamundial.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mapamundial.Model.Paises;
import com.example.mapamundial.R;

import java.util.List;

public class Adapter extends ArrayAdapter<Paises> {

    public Adapter(Context context, List<Paises> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paises paises = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_paises, parent, false);

        }
        TextView tvNome = (TextView) convertView.findViewById(R.id.name);
        TextView tvLocalizacao = (TextView) convertView.findViewById(R.id.region);
        TextView tvCapital = (TextView) convertView.findViewById(R.id.capital);
        TextView tvSubregion = (TextView) convertView.findViewById(R.id.subregion);
        TextView tvPopulation = (TextView) convertView.findViewById(R.id.population);
        TextView tvArea = (TextView) convertView.findViewById(R.id.area);
        TextView tvNumeriCode = (TextView) convertView.findViewById(R.id.numericCode);


        tvNome.setText(paises.name);
        tvLocalizacao.setText("Região: " + paises.region);
        tvCapital.setText("Capital: " + paises.capital);
        tvSubregion.setText("SubRegião: " + paises.region2);
        tvPopulation.setText("População: " + paises.population);
        tvArea.setText("Área: " + paises.area);
        tvNumeriCode.setText("DDD: " + paises.numericCode);


        return convertView;

    }
}