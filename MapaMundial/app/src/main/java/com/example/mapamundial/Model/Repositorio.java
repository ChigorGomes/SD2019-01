package com.example.mapamundial.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mapamundial.DAO.SQLHelper;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private SQLHelper helper;
    private SQLiteDatabase db;

    public Repositorio(Context context) {
        helper = new SQLHelper(context);
    }

    public void inserir(Paises paises) {

        db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.COLUNA_NAME, paises.name);
        cv.put(SQLHelper.COLUNA_CAPITAL, paises.capital);
        cv.put(SQLHelper.COLUNA_REGION, paises.region);
        cv.put(SQLHelper.COLUNA_POPULATION, paises.population);
        cv.put(SQLHelper.COLUNA_AREA, paises.area);
        cv.put(SQLHelper.COLUNA_SUBREGIAO, paises.region2);
        cv.put(SQLHelper.COLUNA_NUMERICCODE, paises.numericCode);


        db.insert(SQLHelper.TABELA_PAISES, null, cv);

        db.close();
    }

    public void excluirAll() {
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
            String numericCode = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_NUMERICCODE)
            );
            String subregion = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_SUBREGIAO)
            );


            Paises paises = new Paises(name, capital, region, subregion, population, area, numericCode);

            list.add(paises);


        }
        cursor.close();
        return list;
    }


}
