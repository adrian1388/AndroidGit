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

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.Date;


import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by USUARIO-WIN on 01/02/2015.
 */
public class ProdActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

//    static final double LONGITUD_MIN = -79.88952041990588;    //alsa
//    static final double LONGITUD_MAX = -79.88890887625048;
//    static final double LATITUD_MIN = -2.2269273699942698;
//    static final double LATITUD_MAX = -2.2266137885128807;

    static final double LONGITUD_MIN =-79.59414875129283;     //misa
    static final double LONGITUD_MAX =-79.59382152179302;
    static final double LATITUD_MIN = -2.1205456450053832;
    static final double LATITUD_MAX = -2.1202025573244674;

//    static final double LATITUD_MIN  = -2.1449066116642936;     //ESPOL
//    static final double LONGITUD_MIN = -79.9679602908726;
//    static final double LATITUD_MAX  = -2.144241889736975;
//    static final double LONGITUD_MAX = -79.96730851408198;

    private double posLong = 0;
    private double posLat = 0;

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    int mode = NONE;

    Bitmap Imag;
    int picWidth, picHeight;
    int pix[];

    private Uri mUri;
    private TextView mTvProdName;
    private TextView mTvUbicacion;
    private ImageView mIvCroquis;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_prod);

        Button btn_main = (Button) findViewById(R.id.btn_main);

        Intent intent = getIntent();
        mUri = intent.getData();

        mTvProdName = (TextView) findViewById(R.id.tv_nombreProd);
        mTvUbicacion = (TextView) findViewById(R.id.tv_ubicacion);

        // Invokes the method onCreateloader() in non-ui thread
        getSupportLoaderManager().initLoader(0, null, this);


        mIvCroquis = (ImageView) findViewById(R.id.IvCroquis);

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        mlocListener.setMainActivity(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,(LocationListener)mlocListener);

        posLong = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
        posLat = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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

        this.cursor = cursor;
        if(cursor.moveToFirst()){
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            mTvProdName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)))+": ");
            mTvUbicacion.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
            mIvCroquis.setImageResource(cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(3))));//mIvCroquis.setImageResource(R.drawable.a2);
            PintarLocalizacion();
            ZoomingCroquis(mIvCroquis);
            Toast toast = Toast.makeText(getApplicationContext(), "Tu ubicación es el puto rojo", Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.RED);
            toast.show();
            //Toast.makeText(getApplicationContext(), "Tu ubicación es el puto rojo", Toast.LENGTH_SHORT).show();
            db.insertarHist(Integer.parseInt(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)))),
                            sqlDate);
        }
    }

    public void PintarLocalizacion(){

        Imag = BitmapFactory.decodeResource(getResources(), cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(3))));
        picWidth = Imag.getWidth();
        picHeight = Imag.getHeight();
        pix = new int[picWidth*picHeight];
        Bitmap bm = Bitmap.createBitmap(picWidth, picHeight, Bitmap.Config.ARGB_4444);

        pix = PintarPosicion(Imag);
        bm.setPixels(pix, 0, picWidth, 0, 0, picWidth, picHeight);
        mIvCroquis.setImageBitmap(bm);
        mIvCroquis.invalidate();
    }
    public void ZoomingCroquis(final ImageView Croquis){
        Croquis.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ImageView view = (ImageView) v;
                //System.out.println("matrix=" + savedMatrix.toString());
                Croquis.setScaleType(ImageView.ScaleType.MATRIX);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix);
                        startPoint.set(event.getX(), event.getY());
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(midPoint, event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                        }
                        else if (mode == ZOOM) {
                            float newDist = spacing(event);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = newDist / oldDist;
                                matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                            }
                        }
                        break;
                } view.setImageMatrix(matrix);
                return true;
            }
            @SuppressLint("FloatMath")
            private float spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return FloatMath.sqrt(x * x + y * y);
            }
            private void midPoint(PointF point, MotionEvent event) {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                point.set(x / 2, y / 2);
            }
        });

    }
    public int[] PintarPosicion(Bitmap mBitmap)
    {
        int width, height;
        int x,y;
        double n,m;
        width = mBitmap.getWidth();
        height = mBitmap.getHeight();
        int[] pix = new int[width * height];
        mBitmap.getPixels(pix, 0, width, 0, 0, width, height);

        m = (posLong - LONGITUD_MAX) / (LONGITUD_MAX - LONGITUD_MIN);
        x = (-1)*(int) (width * m);
        n = (posLat - LATITUD_MAX) / (LATITUD_MAX - LATITUD_MIN);
        y = (-1)*(int) (height * n);
        int delta=20;
        for (int j = y; j < y+delta; j++)
            for (int i = x; i < x+delta; i++) {
                int posicionEnBitMap = j * width + i;

                pix[posicionEnBitMap] = Color.RED;

            }
        return pix;
    }
    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }

    public class MyLocationListener implements LocationListener {
        FragmentActivity myActivity;

        public FragmentActivity getMyActivity(){
            return myActivity;
        }

        public void setMainActivity(FragmentActivity myActivity){
            this.myActivity = myActivity;
        }

        @Override
        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();
            posLat = location.getLatitude();
            posLong = location.getLongitude();
            //PintarLocalizacion();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
