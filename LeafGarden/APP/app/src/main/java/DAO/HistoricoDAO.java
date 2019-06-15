package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import BD.BancoDeDados;
import Classe.Historico;
import Classe.Jardim;
import Classe.Usuario;

public class HistoricoDAO {
    private SQLiteDatabase database;

    public HistoricoDAO(Context context){
        this.database= (new BancoDeDados(context)).getWritableDatabase();
    }

    public boolean addHistorico(Historico historico,Jardim jardim){
        try{
            ContentValues contentValues= new ContentValues();
            contentValues.put("dataHorario",historico.getDataHorario());
            contentValues.put("infoTemperatura",historico.getInfoTemperatura());
            contentValues.put("infoUmidade",historico.getInfoUmidade());
            contentValues.put("infoLuminosidade",historico.getInfoLuminosidade());
            contentValues.put("idJardim",jardim.getIdJardim());
            database.insert("historico",null,contentValues);
            return  true;

        }catch (SQLiteException e){
            e.printStackTrace();
            return  false;
        }
    }

    public ArrayList<Historico> getHistorico(Usuario usuario){
        ArrayList<Historico> historicoArrayList= new ArrayList<>();
        try{
            Historico historico=null;
            String sql="SELECT DISTINCT historico.idHistorico,planta.nome,historico.infoLuminosidade,historico.infoTemperatura,historico.infoUmidade,historico.dataHorario " +
                    "FROM jardim,usuario,planta,historico WHERE jardim.idPlanta= planta.idPlanta AND historico.idJardim = jardim.idJardim " +
                    "AND jardim.idUsuario="+ usuario.getIdUsuario() +" order by historico.dataHorario";

            Cursor cursor= this.database.rawQuery(sql,null);
            while (cursor.moveToNext()){
                int idHistorico= cursor.getInt(0);
                String nomePlanta = cursor.getString(1);
                String luminosidade= cursor.getString(2);
                String temperatura= cursor.getString(3);
                String umidade= cursor.getString(4);
                String dataHorario= cursor.getString(5);
                historico = new Historico(idHistorico, dataHorario,temperatura, umidade, luminosidade,nomePlanta);
                historicoArrayList.add(historico);
            }
            return  historicoArrayList;
        }catch (SQLiteException e){
            Log.e("erro",e.getMessage());
        }
        return historicoArrayList;
    }
}
