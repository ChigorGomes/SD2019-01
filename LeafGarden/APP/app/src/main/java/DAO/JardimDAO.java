package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import BD.BancoDeDados;
import Classe.Jardim;
import Classe.Usuario;

public class JardimDAO {
    private SQLiteDatabase database;

    public JardimDAO(Context context) {
        this.database= (new BancoDeDados(context)).getWritableDatabase();
    }

    public boolean addJardim(Jardim jardim){
        try{
            ContentValues contentValues= new ContentValues();
            contentValues.put("idPlanta",jardim.getIdPlanta());
            contentValues.put("idUsuario",jardim.getIdUsuario());
            database.insert("jardim",null,contentValues);
            return  true;
        }catch (SQLiteException e){
            e.printStackTrace();
            return  false;
        }
    }


    public ArrayList<Jardim> getJardim(Usuario usuario){
        ArrayList<Jardim> jardimArrayList= new ArrayList<>();

        try{
            Jardim jardim=null;
            String sql= "SELECT DISTINCT jardim.idJardim,jardim.idPlanta,jardim.idUsuario, planta.nome, planta.tempAmbiente,planta.umidadeAmbiente," +
                        "planta.tempSolo,planta.umidadeSolo,planta.luminosidade,planta.foto " +
                        "FROM jardim,planta,usuario " +
                        "WHERE jardim.idPlanta = planta.idPlanta" +//+ planta.getIdPlanta()+
                        " AND jardim.idUsuario="+usuario.getIdUsuario();

            Cursor cursor = this.database.rawQuery (sql,null);

            while (cursor.moveToNext()){
                int idJardim=cursor.getInt(0);
                int idPlanta=cursor.getInt(1);
                int idUsuario=cursor.getInt(2);
                String nomePlanta=cursor.getString(3);
                float tempAmbiente=cursor.getFloat(4);
                float umidadeAmbiente=cursor.getFloat(5);
                float tempSolo=cursor.getFloat(6);
                float umidadeSolo=cursor.getFloat(7);
                float luminosidade=cursor.getFloat(8);

                jardim= new Jardim(idJardim,idPlanta,idUsuario,nomePlanta,tempAmbiente,umidadeAmbiente,tempSolo,umidadeSolo,luminosidade,cursor.getBlob(9));
                jardimArrayList.add(jardim);
            }


            return  jardimArrayList;

        }catch (SQLiteException e){
            Log.e("Erro", e.getMessage());


        }
        return jardimArrayList;


    }
}

