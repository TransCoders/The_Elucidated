package gr.edu.serres.TrancCoder_TheElucitated;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Damian on 11/21/2016.
 */

public class ProximityPoint {
    private String geo;
    private double lat, lon;
    float radius=100;
    private final String PROX_ALERT = "app.test.PROXIMITY_ALERT";
    Intent intent;
    PendingIntent pendingIntent;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    ProximityPoint(Context context, DummyItem item) {
        lat = item.getLocation().latitude;
        lon = item.getLocation().longitude;
        geo = "geo:" + lat + "," + lon;
        intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", item.getName());
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    }

    public void addProximityAlert(Context context, LocationManager locationManager) {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                /*&& ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
            // TODO: Consider calling
            return;
        }
        locationManager.addProximityAlert(lat, lon, radius, -1, pendingIntent);
    }

    public void removePendingIntent(Context context,LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                /*&& ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
            // TODO: Consider calling
            return;
        }
        locationManager.removeProximityAlert(pendingIntent);
    }

}
