package com.example.mapamundial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mapamundial.Adapter.Adapter;

import com.example.mapamundial.Model.LatiLong;
import com.example.mapamundial.Model.Paises;
import com.example.mapamundial.Model.Repositorio;
import com.example.mapamundial.Util.HttpRetro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Serializable, SwipeRefreshLayout.OnRefreshListener {
    private Adapter adapter;
    private List<Paises> paisesList;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<LatiLong> latiArrayList;


    Repositorio db;


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Repositorio(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setColorScheme(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.listView);
        paisesList = new ArrayList<Paises>();
        adapter = new Adapter(this, paisesList);
        getDataRetro();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hasPermission();
                if (isConnected()) {

                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("paises", paisesList.get(position));
                    startActivity(intent);
                }
            }
        });

    }


    private void getDataSqlite() {
        paisesList.clear();
        paisesList.addAll(db.listarPaises());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        getDataRetro();

    }


    public void getDataRetro() {

        swipeRefreshLayout.setRefreshing(true);
        if (isConnected()) {
            latiArrayList = new ArrayList<>();

            HttpRetro.getCountryClient().getCountry().enqueue(new Callback<List<Paises>>() {
                public void onResponse(Call<List<Paises>> call, Response<List<Paises>> response) {
                    if (response.isSuccessful()) {
                        List<Paises> bodyPaises = response.body();
                        paisesList.clear();

                        db.excluirAll();

                        for (Paises paises : bodyPaises) {
                            paisesList.add(paises);
                            db.inserir(paises);
                            if (paises.region2.equals("South America")) {
                                String capital = paises.capital;
                                Double lat = Double.parseDouble(paises.latlng.get(0));
                                Double lgt = Double.parseDouble(paises.latlng.get(1));
                                LatiLong latiLong = new LatiLong(capital, lat, lgt);

                                latiArrayList.add(latiLong);

                            }
                        }


                        adapter.notifyDataSetChanged();
                    } else {
                        System.out.println(response.errorBody());
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<Paises>> call, Throwable t) {
                    t.printStackTrace();
                }

            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, "Sem Conexão, listando Países do banco...", Toast.LENGTH_SHORT).show();
            getDataSqlite();
        }

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.mostrarAmerica:

                hasPermission();
                if (isConnected()) {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                    intent.putExtra("nome", latiArrayList);

                    startActivity(intent);
                    return true;

                }

            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

    public Boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();

        }

        return false;
    }


    void hasPermission() {
        //pede permissao de localizacao
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // ja pediu permissao?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                // solicita permissao de localizacao
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
    }


}
