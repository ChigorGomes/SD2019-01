package com.example.mapamundial;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mapamundial.Model.LatiLong;
import com.example.mapamundial.Model.Paises;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Paises paises;
    double latitude;
    double longitude;
    private GoogleMap mMap;
    private LatLng[] MAPA;
    private ArrayList<LatiLong> nomeCapital;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //pega ubs passada pela main
        paises = (Paises) getIntent().getSerializableExtra("paises");
        nomeCapital = (ArrayList<LatiLong>) getIntent().getSerializableExtra("nome");


        if (nomeCapital != null) {
            int tamanho = nomeCapital.size();
            MAPA = new LatLng[nomeCapital.size()];
            for (int i = 0; i < tamanho; i++) {

                latitude = Double.parseDouble(String.valueOf(nomeCapital.get(i).getLatitude()));
                longitude = Double.parseDouble(String.valueOf(nomeCapital.get(i).getLongitude()));
                MAPA[i] = new LatLng(latitude, longitude);
            }


        }


        //inicializa mapFragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //coordenadas da capital
        if (paises == null) {
            for (int i = 0; i < MAPA.length; i++) {
                LatLng ubslatlng = new LatLng(MAPA[i].latitude, MAPA[i].longitude);
                mMap.addMarker(new MarkerOptions().position(ubslatlng).title(nomeCapital.get(i).getCapital()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubslatlng, 3));
            }


        } else if (paises != null) {
            LatLng unlacing = new LatLng(Double.parseDouble(paises.latlng.get(0)), Double.parseDouble(paises.latlng.get(1)));

            googleMap.addMarker(new MarkerOptions().position(unlacing).title(paises.capital));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(unlacing, 3));


        }


    }


}



