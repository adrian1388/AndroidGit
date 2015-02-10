package hm.ProyLocate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.Date;

/**
 * Created by USUARIO-WIN on 01/02/2015.
 */
public class ProdActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

    private Uri mUri;
    private TextView mTvProdName;
    private TextView mTvUbicacion;
    private ListView mLvlistaHistorial;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_prod);

        Intent intent = getIntent();
        mUri = intent.getData();

        mTvProdName = (TextView) findViewById(R.id.tv_nombreProd);
        mTvUbicacion = (TextView) findViewById(R.id.tv_ubicacion);
        mLvlistaHistorial = (ListView) findViewById(R.id.listaHistorial);

        // Invokes the method onCreateloader() in non-ui thread
        getSupportLoaderManager().initLoader(0, null, this);

    }

    /** Invoked by initLoader() */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(getBaseContext(), mUri, null, null , null, null);
    }
    SuperDB  manager = new SuperDB(this);
    BaseHelper db = new BaseHelper(this, SuperDB.DBNAME, null, SuperDB.VERSION);
    /** Invoked by onCreateLoader(), will be executed in ui-thread */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        String[] from = new String[]{manager.COLUMN_ID_PROD,manager.COLUMN_UBICACION};
        int[] to = new int[]{android.R.id.text1,android.R.id.text2};
        if(cursor.moveToFirst()){
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            mTvProdName.setText("Producto: " + cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
            mTvUbicacion.setText("Ubicacion: " + cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
            db.insertarHist(Integer.parseInt(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)))),
                            sqlDate);
//            db.consultarHist();
        }
//        Cursor c = db.getReadableDatabase().rawQuery("SELECT "
//                + SuperDB.TABLE_PROD + "." + SuperDB.COLUMN_NAME_PROD + ", "
//                + SuperDB.TABLE_HIST + "." + SuperDB.COLUMN_DATE + " = "
//                + " FROM " + SuperDB.TABLE_PROD + ", " + SuperDB.TABLE_HIST
//                + " WHERE "
//                + SuperDB.TABLE_PROD + "." + SuperDB.COLUMN_ID_PROD + " = "
//                + SuperDB.TABLE_HIST + "." + SuperDB.COLUMN_ID_PROD + ";",null);
//        if (c.isAfterLast() == false){
//            c.moveToNext();
//        }
//        ListAdapter adapter = new SimpleCursorAdapter(this,
//                android.R.layout.activity_list_item,
//                c,
//                from,
//                to,
//                0 );
//        mLvlistaHistorial.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }
}
