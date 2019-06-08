package BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados  extends SQLiteOpenHelper {

    public BancoDeDados(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public static  final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="LeaFGarden.db";


    private static final String TABELA_USUARIO="CREATE TABLE usuario(" +
            "idUsuario  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "nome VARCHAR (45)," +
            "email VARCHAR (60)," +
            "senha VARCHAR (50) ," +
            "idade INTEGER ," +
            "IdRegiao INTEGER);";


    private static final String TABELA_PLANTA="CREATE TABLE planta (" +
            "idPlanta INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "nome  TEXT," +
            "descricao  TEXT," +
            "localAdequado  TEXT," +
            "tempAmbiente REAL," +
            "umidadeAmbiente REAL," +
            "tempSolo REAL," +
            "umidadeSolo REAL," +
            "luminosidade REAL," +
            "foto BLOB );";

    private static final String SQL_DELETE_TABELA_USUARIO="DROP TABLE IF EXISTS usuario";
    private static final String SQL_DELETE_TABELA_PLANTA="DROP TABLE IF EXISTS planta";


    public BancoDeDados(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABELA_USUARIO);
        db.execSQL(TABELA_PLANTA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABELA_USUARIO);
        db.execSQL(SQL_DELETE_TABELA_PLANTA);
        onCreate(db);

    }


}
