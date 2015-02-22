package rcreativity.locate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

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

//        // Defining table Super
//        sqlSuper =  "create table "
//                + SuperDB.TABLE_SUPER + "("
//                + SuperDB.COLUMN_ID_SUPER + " integer primary key autoincrement, "
//                + SuperDB.COLUMN_NAME_SUPER + " text , "
//                + SuperDB.COLUMN_LATITUD + " text , "
//                + SuperDB.COLUMN_LONGITUD + " text , "
//                + SuperDB.COLUMN_LOGO + " text not null);";
//        db.execSQL(sqlSuper);

/////////////////////////////////////////////////////////////

        sqlSuper = " create table " + SuperDB.TABLE_NAME + " ("
                + SuperDB.CN_ID + " integer primary key autoincrement, "
                + SuperDB.CN_NAME + " text not null, "
                + SuperDB.CN_LATITUD + " text not null, "
                + SuperDB.CN_LONGITUD + " text not null);";
        db.execSQL(sqlSuper);

////////////////////////////////////////////////////////////////////////
        // Defining table Producto
        sqlProd =  "create table "
                + SuperDB.TABLE_PROD + "("
                + SuperDB.COLUMN_ID_PROD + " integer primary key autoincrement, "
                + SuperDB.COLUMN_NAME_PROD + " text , "
                + SuperDB.COLUMN_UBICACION + " text , "
                + SuperDB.COLUMN_CROQUIS + " integer);";
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
//
//        for(int i=0;i< Supermercados.nombreSuper.length;i++){
//
//            // Defining insert statement
//            sqlSuper = "insert into "
//                    + SuperDB.TABLE_SUPER + "("
//                    + SuperDB.COLUMN_NAME_SUPER + " , "
//                    + SuperDB.COLUMN_LATITUD + " , "
//                    + SuperDB.COLUMN_LONGITUD + " , "
//                    + SuperDB.COLUMN_LOGO + " ) "
//                    + " values ( "
//                    + " '" + Supermercados.nombreSuper[i] + "' ,"
//                    + " '" + Supermercados.latitud[i] + "' ,"
//                    + " '" + Supermercados.longitud[i] + "' ,"
//                    + " '" + Supermercados.logo[i] + "' ) ";
//
//            // Inserting values into table
//            db.execSQL(sqlSuper);
//        }

/////////////////////////////////////////////////////////////////////////

        for(int i=0;i< Supermercados.nombreSuper.length;i++){

            // Defining insert statement
            sqlSuper = "insert into "
                    + SuperDB.TABLE_NAME + "("
                    + SuperDB.CN_NAME + " , "
                    + SuperDB.CN_LATITUD + " , "
                    + SuperDB.CN_LONGITUD + " ) "
                    + " values ( "
                    + " '" + Supermercados.nombreSuper[i] + "' ,"
                    + " '" + Supermercados.latitud[i] + "' ,"
                    + " '" + Supermercados.longitud[i] + "' ) ";

            // Inserting values into table
            db.execSQL(sqlSuper);
        }

//////////////////////////////////////////////////////////////////////////

        for(int i=0;i< Productos.productos.length;i++){

            // Defining insert statement
            sqlProd = "insert into "
                    + SuperDB.TABLE_PROD + " ( "
                    + SuperDB.COLUMN_NAME_PROD + " , "
                    + SuperDB.COLUMN_UBICACION + " , "
                    + SuperDB.COLUMN_CROQUIS + " ) "
                    + " values ( "
                    + " '" + Productos.productos[i] + "' ,"
                    + " '" + Productos.ubicacion[i] + "' ,"
                    + " '" + Productos.croquis[i] + "' ) ";

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

    public Cursor cargarCursorSuperMercados()
    {
        Cursor c = getReadableDatabase().rawQuery("SELECT "
                + SuperDB.CN_ID + " AS _id, "
                + SuperDB.CN_NAME + " AS nameSuper, "
                + SuperDB.CN_LATITUD + " AS latitud, "
                + SuperDB.CN_LONGITUD + " AS longitud "
                + " FROM " + SuperDB.TABLE_NAME,null);
        return c;
//        String[] columnas = new String[]{SuperDB.CN_ID,SuperDB.CN_NAME,SuperDB.CN_LATITUD,SuperDB.CN_LONGITUD};
//        return  getReadableDatabase().query(SuperDB.TABLE_NAME, columnas, null, null, null, null, null);
    }

//    public Cursor cargarCursorSuperMercados()
//    {
//        String[] columnas = new String[]{SuperDB.COLUMN_ID_SUPER,SuperDB.COLUMN_NAME_SUPER,SuperDB.COLUMN_LATITUD,SuperDB.COLUMN_LONGITUD};
//        return  getReadableDatabase().query(SuperDB.TABLE_SUPER,columnas,null,null,null,null,null);
//    }

    public Cursor consultarHist(){

        Cursor c = getReadableDatabase().rawQuery("SELECT "
                + "DISTINCT "
                + "b." + SuperDB.COLUMN_NAME_PROD + " AS _id, "
                + "a." + SuperDB.COLUMN_DATE + " AS fecha"
                + " FROM " + SuperDB.TABLE_PROD + " b, " + SuperDB.TABLE_HIST + " a"
                + " WHERE "
                + "a." + SuperDB.COLUMN_ID_PROD + " = "
                + "b." + SuperDB.COLUMN_ID_PROD ,null);
        return c;
    }

    public Cursor consultarHistPorFecha(Date fecha){

        Cursor c = getReadableDatabase().rawQuery("SELECT "
                + "DISTINCT "
                + "b." + SuperDB.COLUMN_NAME_PROD + " AS _id, "
                + "a." + SuperDB.COLUMN_DATE + " AS fecha"
                + " FROM " + SuperDB.TABLE_PROD + " b, " + SuperDB.TABLE_HIST + " a"
                + " WHERE "
                + "a." + SuperDB.COLUMN_ID_PROD + " = "
                + "b." + SuperDB.COLUMN_ID_PROD + " AND "
                + "a." + SuperDB.COLUMN_DATE + " = "
                + "'" + String.valueOf(fecha) + "'",null);
        return c;
    }
    public Cursor getCroquisProd(String nombreProd){

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(SuperDB.TABLE_PROD);

        Cursor c = queryBuilder.query(getReadableDatabase(),
                new String[] { "nombreProd", "croquis" } ,
                "nombreProd = ?", new String[] { nombreProd } , null, null, null ,"1"
        );

        return c;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}