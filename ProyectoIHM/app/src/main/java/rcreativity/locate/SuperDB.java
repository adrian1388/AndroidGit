/*+++++++++++++++++++++++++++++++++++++++++++++++++++++/
 *
 * Héctor Mosquera
 *
 * Giannina Cicenia
 *											rCreativity
 * Alvaro Atariguana
 *
 * David Vinces
 *
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package rcreativity.locate;


import java.sql.Date;
import java.util.HashMap;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by USUARIO-WIN on 01/02/2015.
 */
public class SuperDB {

    public static final String DBNAME = "LocateSuper";
    public static final int VERSION = 1;
    private BaseHelper mBaseHelper;

    //public static final String TABLE_SUPER = "supermercado";
    public static final String TABLE_PROD = "producto";
    public static final String TABLE_HIST = "historial";

//    public static final String COLUMN_ID_SUPER = "_idSuper";
//    public static final String COLUMN_NAME_SUPER = "nombreSuper";
//    public static final String COLUMN_LATITUD = "latitud";
//    public static final String COLUMN_LONGITUD = "longitud";
//    public static final String COLUMN_LOGO = "logo";

    public static final String COLUMN_ID_PROD = "_idProd";
    public static final String COLUMN_NAME_PROD = "nombreProd";
    public static final String COLUMN_UBICACION = "ubicacion";
    public static final String COLUMN_CROQUIS = "croquis";

    public static final String COLUMN_ID_HIST = "_idHist";
    public static final String COLUMN_DATE = "fecha";

/////////////////////////////////////////////////////////////////

    public static final String TABLE_NAME = "superMercados";

    public static final String CN_ID = "_id";
    public static final String CN_NAME = "razonSocial";
    public static final String CN_LATITUD = "latitud";
    public static final String CN_LONGITUD = "longitud";

///////////////////////////////////////////////////////////////////


    private HashMap<String, String> mAliasMap;

    public SuperDB(Context context){
        mBaseHelper = new BaseHelper(context, DBNAME, null, VERSION);

        // This HashMap is used to map table fields to Custom Suggestion fields
        mAliasMap = new HashMap<String, String>();

        // Unique id for the each Suggestions ( Mandatory )
        mAliasMap.put("_ID", COLUMN_ID_PROD + " as " + "_id" );

        // Text for Suggestions ( Mandatory )
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, COLUMN_NAME_PROD + " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);


        // This value will be appended to the Intent data on selecting an item from Search result or Suggestions ( Optional )
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, COLUMN_ID_PROD + " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID );


        // Icon for Suggestions ( Optional )
        //mAliasMap.put(SearchManager.SUGGEST_COLUMN_ICON_1, COLUMN_CROQUIS + " as " + SearchManager.SUGGEST_COLUMN_ICON_1);
    }

    /** Returns Countries */
    public Cursor getProductos(String[] selectionArgs){

        String selection = COLUMN_NAME_PROD + " like ? ";

        if(selectionArgs!=null){
            selectionArgs[0] = "%"+selectionArgs[0] + "%";
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setProjectionMap(mAliasMap);

        queryBuilder.setTables(TABLE_PROD);

        Cursor c = queryBuilder.query(mBaseHelper.getReadableDatabase(),
                new String[] { "_ID",
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID/*,
                        SearchManager.SUGGEST_COLUMN_ICON_1*/ } ,
                selection,
                selectionArgs,
                null,
                null,
                COLUMN_NAME_PROD + " asc ","10"
        );
        return c;
    }

    /** Return Country corresponding to the id */
    public Cursor getProducto(String id){

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_PROD);

        Cursor c = queryBuilder.query(mBaseHelper.getReadableDatabase(),
                new String[] { "_idProd", "nombreProd", "ubicacion", "croquis" } ,
                "_idProd = ?", new String[] { id } , null, null, null ,"1"
        );

        return c;
    }

}
