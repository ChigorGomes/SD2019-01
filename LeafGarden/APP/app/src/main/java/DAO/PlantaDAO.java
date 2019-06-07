package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import BD.BancoDeDados;
import Classe.Planta;

public class PlantaDAO {
    private SQLiteDatabase database; //db
    private BancoDeDados bancoDeDados; //banco

    public PlantaDAO(Context context) {
        this.database = (new BancoDeDados(context)).getWritableDatabase();

    }

    public boolean addPlanta(Planta planta){
        try{
            String sql="INSERT INTO planta VALUES(NULL,'"+ planta.getNomePlanta()+"','"+planta.getDescricao()+"','"+
                    planta.getLocalAdequado()+"',"+planta.getTempAmbiente()+","+planta.getUmidadeAmbiente()+","+
                    planta.getTempSolo()+","+planta.getUmidadeSolo()+","+planta.getLuminosidade()+",'"+planta.getFoto()+"')";
            this.database.execSQL(sql);
            return  true;

        }catch (SQLException e){
            Log.e("erro",e.getMessage());
            return  false;
        }
    }

    public Cursor getPlanta(){
        String sql="SELECT rowid as _id, nome,foto FROM  planta ";

        return this.database.rawQuery(sql,null);
    }


}
