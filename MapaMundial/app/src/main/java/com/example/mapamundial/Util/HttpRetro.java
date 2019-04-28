package com.example.mapamundial.Util;

import android.util.Log;

import com.example.mapamundial.Model.Paises;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HttpRetro {

    private static final String BASE_URL = "http://restcountries.eu";
    private static final String PAISES_URL_JSON = "/rest/v1";


    // Interface com métodos de requisicao
    public interface PaisesInterface {
        @GET(PAISES_URL_JSON + "/all")
        Call<List<Paises>> getCountry();
    }

    //Inicializa Retrofit
    public static PaisesInterface getCountryClient() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Log.e("msg","passou");

        return restAdapter.create(PaisesInterface.class);

    }

}
