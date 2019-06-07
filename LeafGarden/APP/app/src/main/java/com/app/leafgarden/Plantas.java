package com.app.leafgarden;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.File;

import Classe.Planta;
import DAO.PlantaDAO;
import DAO.UsuarioDAO;

public class Plantas extends ListActivity {

    PlantaDAO plantaDAO;
    Cursor cursor;
    SimpleCursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        plantaDAO= new PlantaDAO(Plantas.this);
        cursor = plantaDAO.getPlanta();


        String[] nomeCampos= new String[]{"nome","foto"};
        int[] idVies= new int[]{R.id.nomePlantList,R.id.textViewTeste};





        if(cursor.getCount()>0){

            try{
                cursorAdapter= new SimpleCursorAdapter(this,R.layout.activity_plantas,plantaDAO.getPlanta(),nomeCampos,idVies,0);
                setListAdapter(cursorAdapter);
            }catch (Exception e){
                Log.e("erro",e.getMessage());
            }

        }else{
            Toast.makeText(this,"Nenhum registro encontrado!",Toast.LENGTH_SHORT).show();
        }


    }

    }
