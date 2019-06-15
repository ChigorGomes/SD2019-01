package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import BD.BancoDeDados;
import Classe.Usuario;

public class UsuarioDAO {
    private SQLiteDatabase database;

    public UsuarioDAO(Context context) {
        this.database = (new BancoDeDados(context)).getWritableDatabase();
    }

    public boolean addUsuario(Usuario usuario){
        try{
            if(verificaUsuario(usuario)){
                return false;
            }else {
                ContentValues contentValues= new ContentValues();
                contentValues.put("nome",usuario.getNome());
                contentValues.put("email", usuario.getEmail());
                contentValues.put("senha",usuario.getSenha());
                contentValues.put("idade",usuario.getIdade());
                contentValues.put("idRegiao",usuario.getIdRegiao());
                database.insert("usuario",null,contentValues);
                return  true;
            }
        }catch (SQLException e){
            Log.e("erro",e.getMessage());
            return  false;
        }
    }

    public  boolean editarUsuario(Usuario usuario){
        try{
            ContentValues contentValues= new ContentValues();
            contentValues.put("nome",usuario.getNome());
            contentValues.put("senha",usuario.getSenha());
            contentValues.put("idRegiao",usuario.getIdRegiao());
            database.update("usuario",contentValues,"idUsuario="+usuario.getIdUsuario(),null);
            return true;
        }catch (SQLiteException e){
            Log.e("erro",e.getMessage());
            return false;
        }
    }

    public Usuario getUsuario(String email,String senha){
        Usuario usuario=null;
        String sql="SELECT * FROM usuario WHERE email='"+email+"' AND senha='"+senha+"'";
        Cursor cursor= this.database.rawQuery(sql,null);
        if(cursor.moveToNext()){
            usuario= new Usuario(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),
                    cursor.getInt(5));
        }cursor.close();
        return usuario;

    }

    public boolean verificaUsuario(Usuario usuario){
        String sql= "SELECT * FROM usuario WHERE email='"+ usuario.getEmail()+"'";
        Cursor cursor=  this.database.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            return  true;
        }cursor.close();
        return false;
    }




}
