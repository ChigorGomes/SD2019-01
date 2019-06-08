package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
                String sql = "INSERT INTO usuario VALUES (NULL,'" + usuario.getNome() + "','" + usuario.getEmail() + "','" + usuario.getSenha() + "',"
                        + usuario.getIdade() + "," + usuario.getIdRegiao() + ")";
                this.database.execSQL(sql);
                return true;
            }
        }catch (SQLException e){
            Log.e("erro",e.getMessage());
            return  false;
        }
    }

    public Usuario getUsuario(String email,String senha){
        Usuario usuario=null;
        String sql="SELECT * FROM usuario WHERE email='"+email+"' AND senha='"+senha+"'";
        Cursor cursor= this.database.rawQuery(sql,null);
        if(cursor.moveToNext()){
            usuario= new Usuario(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),
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
