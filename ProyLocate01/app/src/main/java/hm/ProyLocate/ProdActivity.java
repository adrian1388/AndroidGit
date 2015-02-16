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


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_prod);

        Intent intent = getIntent();
        mUri = intent.getData();

        mTvProdName = (TextView) findViewById(R.id.tv_nombreProd);
        mTvUbicacion = (TextView) findViewById(R.id.tv_ubicacion);

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
        if(cursor.moveToFirst()){
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            mTvProdName.setText("Producto: " + cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
            mTvUbicacion.setText("Ubicacion: " + cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
            db.insertarHist(Integer.parseInt(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)))),
                            sqlDate);
//            db.consultarHist();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }
}
