package rcreativity.locate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Margie on 14/02/2015.
 */
public class DataBaseManager {
    public static final String TABLE_NAME = "superMercados";

    public static final String CN_ID = "_id";
    public static final String CN_NAME = "razonSocial";
    public static final String CN_LATITUD = "latitud";
    public static final String CN_LONGITUD = "longitud";

    public static final String CREATE_TABLE = " create table " + TABLE_NAME + " ("
            + CN_ID + " integer primary key autoincrement, "
            + CN_NAME + " text not null, "
            + CN_LATITUD + " text not null, "
            + CN_LONGITUD + " text not null);";

    private  DbHelper helper;
    private SQLiteDatabase db;
    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public  ContentValues generarContentValues(String nombre, String latitud, String longitud)
    {
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME,nombre);
        valores.put(CN_LATITUD,latitud);
        valores.put(CN_LONGITUD,longitud);
        return  valores;
    }
    public void insertar(String nombre, String latitud, String longitud)
    {
        db.insert(TABLE_NAME,null, generarContentValues(nombre,  latitud,  longitud));
    }

    public void eliminar()
    {
        db.delete(TABLE_NAME,null,null);
    }

    public Cursor cargarCursorSuperMercados()
    {
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_LATITUD,CN_LONGITUD};
        return  db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }
}
