package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import BD.BancoDeDados;
import Classe.Planta;

public class PlantaDAO {
    private SQLiteDatabase database; //db


    public PlantaDAO(Context context) {
        this.database = (new BancoDeDados(context)).getWritableDatabase();

    }

    public boolean addPlanta(String name,Planta planta){
        try{
            FileInputStream stream= new FileInputStream(name);
            byte[] imBytes= new byte[stream.available()];
            stream.read(imBytes);
           ContentValues contentValues= new ContentValues();
            contentValues.put("nome",planta.getNomePlanta());
            contentValues.put("descricao", planta.getDescricao());
            contentValues.put("localAdequado",planta.getLocalAdequado());
            contentValues.put("tempAmbiente",planta.getTempAmbiente());
            contentValues.put("umidadeAmbiente",planta.getUmidadeAmbiente());
            contentValues.put("tempSolo",planta.getTempSolo());
            contentValues.put("umidadeSolo",planta.getUmidadeSolo());
            contentValues.put("luminosidade",planta.getLuminosidade());
            contentValues.put("foto",imBytes);
            database.insert("planta",null,contentValues);

            stream.close();
            return  true;
        }catch (IOException e){
            e.printStackTrace();
            return  false;
        }
    }




    public ArrayList<Planta> getPlanta(){
        ArrayList<Planta> plantaVector= new ArrayList<>();

        try{
            Planta planta= null;
            String sql= "SELECT * from planta ORDER BY nome";

            Cursor cursor = this.database.rawQuery (sql,null);

            while (cursor.moveToNext()){
                    planta = new Planta(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                            cursor.getFloat(4),cursor.getFloat(5),cursor.getFloat(6),cursor.getFloat(7),cursor.getFloat(8),cursor.getBlob(9));
                    plantaVector.add(planta);
                }


            return  plantaVector;

        }catch (SQLiteException e){
            Log.e("Erro", e.getMessage());


        }
        return plantaVector;


    }



    }



