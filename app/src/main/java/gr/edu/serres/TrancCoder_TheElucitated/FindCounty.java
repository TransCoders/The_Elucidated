package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by Damian on 12/13/2016.
 */

public class FindCounty extends AsyncTask<LocationManager,String,String> implements LocationListener {


    private final Context mContext;
    LocationManager locationManager;
    Location mLastLocation ;

    public FindCounty(Context context,LocationManager locationManager){
        this.mContext = context;
        this.locationManager = locationManager;
    }
    
    @Override
    protected void onPreExecute(){

        if(checkPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }


    }

    @Override
    protected String doInBackground(LocationManager... locationManager) {
        String stateName= null;
        publishProgress("Searching for GPS location");

        if(checkPermission()){
        while(mLastLocation==null){
                mLastLocation = locationManager[0].getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        }
        publishProgress("GPS location found");
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
           stateName = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stateName;
    }

    @Override
    protected void onProgressUpdate(String... values){
        Toast.makeText(mContext,values[0],Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String stateName){
        Intent myIntent = new Intent(mContext, MapsActivity.class);
        myIntent.putExtra("StateName",stateName);
        myIntent.putExtra("LastLocationLatitude",mLastLocation.getLatitude());
        myIntent.putExtra("LastLocationLongitude",mLastLocation.getLongitude());
        mContext.startActivity(myIntent);
    }


    protected boolean checkPermission(){
        return ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
