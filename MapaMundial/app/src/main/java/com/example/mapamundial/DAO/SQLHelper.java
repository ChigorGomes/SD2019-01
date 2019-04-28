package com.example.mapamundial.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "country";
    private static final int    VERSAO_BANCO = 1;
    public static final String TABELA_PAISES ="paises_tabela";
    public static final String COLUNA_NAME ="name";
    public static final String COLUNA_CAPITAL ="capital";
    public static final String COLUNA_REGION ="region";
    public static final String  COLUNA_POPULATION = "population";
    public static final String COLUNA_AREA ="area";
    public static  final String COLUNA_SUBREGIAO= "subregiao";
    public static  final String COLUNA_NUMERICCODE = "numerico";



    /*
      public Paises(String name, String capital, String region, String population, String area, String numericCode , String subregion)
     */




    public SQLHelper(Context context){
        super(context,NOME_BANCO,null,VERSAO_BANCO);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(
               "CREATE TABLE " + TABELA_PAISES + " ( " +
                        COLUNA_NAME +  " TEXT NOT NULL, "+
                        COLUNA_CAPITAL + " TEXT, " +
                        COLUNA_REGION + " TEXT, " +
                       COLUNA_POPULATION + " TEXT, " +
                       COLUNA_SUBREGIAO + " TEXT, " +
                       COLUNA_NUMERICCODE + " TEXT, " +
                       COLUNA_AREA + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
