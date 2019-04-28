package com.example.mapamundial;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mapamundial.Model.Paises;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Paises paises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //pega ubs passada pela main
        paises = (Paises) getIntent().getSerializableExtra("paises");

        //inicializa mapFragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //coordenadas da capital

        LatLng ubslatlng = new LatLng(Double.parseDouble(paises.latlng.get(0)), Double.parseDouble(paises.latlng.get(1)));

        googleMap.addMarker(new MarkerOptions().position(ubslatlng).title(paises.capital));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubslatlng, 12.0f));
    }
}
