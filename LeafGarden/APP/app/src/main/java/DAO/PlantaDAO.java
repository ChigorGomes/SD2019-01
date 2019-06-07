package DAO;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import BD.BancoDeDados;
import Classe.Planta;

public class PlantaDAO {
    private SQLiteDatabase database;

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

}
