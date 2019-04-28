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
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mapamundial.Adapter.Adapter;

import com.example.mapamundial.Model.Paises;
import com.example.mapamundial.Model.Repositorio;
import com.example.mapamundial.Util.HttpRetro;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Adapter adapter;
    private List<Paises> paisesList;
    private ListView listView;
    private  SwipeRefreshLayout swipeRefreshLayout;
    Repositorio db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Repositorio(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setColorScheme(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.listView);
        paisesList= new ArrayList<Paises>();
        adapter = new Adapter(this,paisesList);
        getDataRetro();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hasPermission();

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("paises",paisesList.get(position));
                startActivity(intent);
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
            HttpRetro.getCountryClient().getCountry().enqueue(new Callback<List<Paises>>() {
                public void onResponse(Call<List<Paises>> call, Response<List<Paises>> response) {
                    if (response.isSuccessful()) {
                        List<Paises> ubsBody = response.body();
                        paisesList.clear();

                        db.excluirAll();

                        for (Paises paises : ubsBody) {
                            paisesList.add(paises);
                            db.inserir(paises);
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

        }else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this,"Sem Conexão, listando Ubs do banco...",Toast.LENGTH_SHORT).show();
            getDataSqlite();
        }

    }


    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( cm != null ) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();

        }
        Log.i("msg","Entrou 2");

        return false;
    }



    void hasPermission(){
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
