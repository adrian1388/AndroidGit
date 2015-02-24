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

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location location;
    private Cursor cursor;
    private Marker mySelectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        BaseHelper manager = new BaseHelper(this, SuperDB.DBNAME, null, SuperDB.VERSION);
          //manager.eliminar();
//          manager.insertar("Plaza Tía" + "Km. 1.2 Autopista Terminal Terrestre - Pascuales C.C.","-2.0652083","-79.9115808");
//          manager.insertar("Tía Expres" + "Terminal Terrestre de Guayaquil","-2.14372","-79.879383");
//          manager.insertar("Gran AKI" + "Terminal Terrestre de Guayaquil","-2.14372","-79.879390");
//          manager.insertar("Megamaxi Mall Los Ceibos","-79.9430305","-79.879390");
//          manager.insertar("Mi Comisariato - Parade Centro del arte","-2.1621545","-79.9377733");

        cursor = manager.cargarCursorSuperMercados();




        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        // Showing status
        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            mMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            mMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
                onLocationChanged(location);
                setUpMap();
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
        }

    }





    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        if (location==null){
            Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainAct);
            finish();
        }
        else {

        }
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        double latitud;
        double longitud;
        double distancia;
        String nombre = "";
        if(cursor.moveToFirst()){
            do{
                latitud = Double.parseDouble(cursor.getString(2));
                longitud = Double.parseDouble(cursor.getString(3));
                nombre = cursor.getString(1);
                distancia = distancia(location.getLatitude(),location.getLongitude(),latitud,longitud);
                if((distancia*1000) <= 25)
                {
                    //estamos a 25 metros del super
                    Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainAct);
                    break;

                }
                else if(distancia > 10){
                    Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainAct);
                    break;
                }
                else if(distancia <= 10) {//solo muestro los que estan a menos de 10km

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            if (marker.equals(mySelectedMarker))
                            {
                                Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainAct);
                                finish();
                            }
                            return false;
                        }
                    });

                    mySelectedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                                    .title(nombre)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    );

                }
                // mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude() + (i / 100), location.getLongitude() + (i / 100))).title(""));
            }while(cursor.moveToNext());
            Toast.makeText(getApplicationContext(), "¡Bienvenido!\nEstos son los Supermercados cercanos a ti.", Toast.LENGTH_LONG).show();
        }
    }

    private double rad(double punto){
        return  punto*Math.PI/180;
    }

    private double distancia(double lat1,double lon1,double lat2,double lon2){
        double Rtierra = 6378.137;
        double dlat = rad(lat2-lat1);
        double dlong = rad(lon2-lon1);
        double a = Math.sin(dlat/2)* Math.sin(dlat/2)+ Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.sin(dlong/2) * Math.sin(dlong/2);
        double c =  2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d =  Rtierra * c;
        return d;
    }

    @Override
    public void onLocationChanged(Location location) {
        // TextView tvLocation = (TextView) findViewById(R.id.tv_location);

        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        // Setting latitude and longitude in the TextView tv_location
        // tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
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
