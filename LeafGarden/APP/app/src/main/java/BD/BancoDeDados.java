package BD;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados  extends SQLiteOpenHelper {

    public static  final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="LeaFGarden.db";
    private static final String TABELA_USUARIO="CREATE TABLE usuario(" +
            "idUsuario  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "nome VARCHAR (45)," +
            "email VARCHAR (60)," +
            "senha VARCHAR (50) ," +
            "idade INTEGER ," +
            "IdRegiao INTEGER);";
    private static final String SQL_DELETE_TABELA_USUARIO="DROP TABLE IF EXISTS usuario";


    public BancoDeDados(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABELA_USUARIO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABELA_USUARIO);
        onCreate(db);
    }
}
