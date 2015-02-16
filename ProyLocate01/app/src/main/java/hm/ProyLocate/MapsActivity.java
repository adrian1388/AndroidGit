package hm.ProyLocate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private SupportMapFragment soporteMap;
    private View v;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        soporteMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        v = soporteMap.getView();
        v.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new LoadHilo2().execute("Variable no usada");
        // setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                setUpMap();
            }
        }
    }


    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(-2.144681463947058, -79.96662589188769)).title("Marker"));
    }

    private class LoadHilo2 extends AsyncTask<String, Float, Integer> {
        private GoogleMap mMapH2; // Might be null if Google Play services APK is not available.
        private SupportMapFragment soporteMapH2;
        private View vH2;
        private ProgressBar progressBarH2;

        public LoadHilo2() {   }

        @Override
        protected void onPreExecute() {
            soporteMapH2 = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            vH2 = soporteMap.getView();
            //setUpMapIfNeeded();
            progressBarH2 = (ProgressBar) findViewById(R.id.progressBar);
        }


        @Override
        protected Integer doInBackground(String... variableNoUsada) {
            int i =  0;
            while (i<200)
            {
                i++;
                try {
                    Thread.sleep((long) (Math.random()*10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap != null)
                return 1;
            else
                return 0;
        }


        @Override
        protected void onPostExecute(Integer bandera) {
            if (bandera == 1)
            {
                progressBarH2.setVisibility(View.GONE);
                vH2.setVisibility(View.VISIBLE);
            }
        }


    }
}
