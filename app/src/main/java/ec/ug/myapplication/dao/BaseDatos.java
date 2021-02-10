package ec.ug.myapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE USUARIO(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT" +
            ",nombre TEXT" +
            ",cedula TEXT" +
            ",edad INTEGER" +
            ",telefono TEXT" +
            ",correo TEXT" +
            ",usuario TEXT" +
            ",clave TEXT" +
            ",fecha_nacimiento TEXT" +
            ",genero TEXT" +
            ");";
    private static final String DB_NAME = "practicasqlite2.sqlite";
    private static final int DB_VERSION = 1;


    /**
     * constructor para crear  o reemplazar la bd y agregar info.
     * @param Context
     */
    public BaseDatos(Context Context){
        super(Context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bdsqlite) {
        bdsqlite.execSQL(COMMENTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
