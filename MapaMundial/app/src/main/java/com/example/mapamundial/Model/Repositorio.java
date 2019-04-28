package com.example.mapamundial.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mapamundial.DAO.SQLHelper;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private SQLHelper helper;
    private SQLiteDatabase db;

    public Repositorio(Context context) {
        helper = new SQLHelper(context);
    }

    public void inserir(Paises paises){

        db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.COLUNA_NAME, paises.name);
        cv.put(SQLHelper.COLUNA_CAPITAL, paises.capital);
        cv.put(SQLHelper.COLUNA_REGION, paises.region);
        cv.put(SQLHelper.COLUNA_POPULATION, paises.population);
//        cv.put(SQLHelper.COLUNA_LATITUDE,paises.latlng.get(0) );
//        cv.put(SQLHelper.COLUNA_LONGITUDE,paises.latlng.get(1) );
        cv.put(SQLHelper.COLUNA_AREA, paises.area);

        db.insert(SQLHelper.TABELA_PAISES,null,cv);

        db.close();
    }

    public void excluirAll(){
        db = helper.getWritableDatabase();
        db.delete(SQLHelper.TABELA_PAISES, null, null);
        db.close();
    }

    public List<Paises> listarPaises() {
        String sql = "SELECT * FROM " + SQLHelper.TABELA_PAISES;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Paises> list = new ArrayList();

        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_NAME)
            );
            String capital = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_CAPITAL)
            );

            String region = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_REGION)
            );
            String population = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_POPULATION)
            );
            String area = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_AREA)
            );
//            List<String> ltd= null;
//            ltd.add(cursor.getString(
//                    cursor.getColumnIndex(SQLHelper.COLUNA_LATITUDE)),cursor.getString(
//                    cursor.getColumnIndex(SQLHelper.COLUNA_LONGITUDE)));



            Paises paises= new Paises( name,  capital,  region,  population, area);
            list.add(paises);
        }
        cursor.close();
        return list;
    }


}
