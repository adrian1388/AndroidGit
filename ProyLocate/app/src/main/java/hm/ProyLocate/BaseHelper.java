package hm.ProyLocate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

/**
 * Created by USUARIO-WIN on 07/02/2015.
 */
class BaseHelper extends SQLiteOpenHelper {

    public BaseHelper(Context context,
                      String name,
                      SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, SuperDB.DBNAME, factory, SuperDB.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlSuper = "";
        String sqlProd = "";
        String sqlHist = "";

        // Defining table Super
        sqlSuper =  "create table "
                + SuperDB.TABLE_SUPER + "("
                + SuperDB.COLUMN_ID_SUPER + " integer primary key autoincrement, "
                + SuperDB.COLUMN_NAME_SUPER + " text , "
                + SuperDB.COLUMN_LATITUD + " text , "
                + SuperDB.COLUMN_LONGITUD + " text , "
                + SuperDB.COLUMN_LOGO + " text not null);";
        db.execSQL(sqlSuper);

        // Defining table Producto
        sqlProd =  "create table "
                + SuperDB.TABLE_PROD + "("
                + SuperDB.COLUMN_ID_PROD + " integer primary key autoincrement, "
                + SuperDB.COLUMN_NAME_PROD + " text , "
                + SuperDB.COLUMN_UBICACION + " text not null);";
        db.execSQL(sqlProd);

        // Defining table Historial
        sqlHist =  "create table "
                + SuperDB.TABLE_HIST + "("
                + SuperDB.COLUMN_ID_HIST + " integer primary key autoincrement, "
                + SuperDB.COLUMN_ID_PROD + " integer not null, "
                + SuperDB.COLUMN_DATE + " text not null, "
                + "FOREIGN KEY(" + SuperDB.COLUMN_ID_PROD + ") REFERENCES "
                + SuperDB.TABLE_PROD + "(" + SuperDB.COLUMN_ID_PROD + "));";
        db.execSQL(sqlHist);

        for(int i=0;i< Supermercados.nombreSuper.length;i++){

            // Defining insert statement
            sqlSuper = "insert into "
                    + SuperDB.TABLE_SUPER + "("
                    + SuperDB.COLUMN_NAME_SUPER + " , "
                    + SuperDB.COLUMN_LATITUD + " , "
                    + SuperDB.COLUMN_LONGITUD + " , "
                    + SuperDB.COLUMN_LOGO + " ) "
                    + " values ( "
                    + " '" + Supermercados.nombreSuper[i] + "' ,"
                    + " '" + Supermercados.latitud[i] + "' ,"
                    + " '" + Supermercados.longitud[i] + "' ,"
                    + " '" + Supermercados.logo[i] + "' ) ";

            // Inserting values into table
            db.execSQL(sqlSuper);
        }

        for(int i=0;i< Productos.productos.length;i++){

            // Defining insert statement
            sqlProd = "insert into "
                    + SuperDB.TABLE_PROD + " ( "
                    + SuperDB.COLUMN_NAME_PROD + " , "
                    + SuperDB.COLUMN_UBICACION + " ) "
                    + " values ( "
                    + " '" + Productos.productos[i] + "' ,"
                    + " '" + Productos.ubicacion[i] + "' ) ";

            // Inserting values into table
            db.execSQL(sqlProd);
        }

    }
    public void insertarHist(int idProd, Date fecha){

        ContentValues valores = new ContentValues();
        valores.put(SuperDB.COLUMN_ID_PROD, idProd);
        valores.put(SuperDB.COLUMN_DATE, String.valueOf(fecha));

        getWritableDatabase().insert(SuperDB.TABLE_HIST,null,valores);

    }
    public void consultarHist(){

        String[] sqlProd = new String[] {""};
        Cursor c = getReadableDatabase().rawQuery("SELECT "
                    + SuperDB.TABLE_PROD + "." + SuperDB.COLUMN_NAME_PROD + ", "
                    + SuperDB.TABLE_HIST + "." + SuperDB.COLUMN_DATE + " = "
                    + " FROM " + SuperDB.TABLE_PROD + ", " + SuperDB.TABLE_HIST
                    + " WHERE "
                    + SuperDB.TABLE_PROD + "." + SuperDB.COLUMN_ID_PROD + " = "
                    + SuperDB.TABLE_HIST + "." + SuperDB.COLUMN_ID_PROD + ";",sqlProd);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}