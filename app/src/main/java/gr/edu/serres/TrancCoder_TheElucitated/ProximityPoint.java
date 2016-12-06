package gr.edu.serres.TrancCoder_TheElucitated;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Damian on 11/21/2016.
 */

public class ProximityPoint {
    private String name;
    private String geo;
    private double lat, lon;
    float radius;
    private final String PROX_ALERT = "app.test.PROXIMITY_ALERT";
    private Intent intent;
    private PendingIntent pendingIntent;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    ProximityPoint(Context context, IMarker iMarker) {
        radius = 200f;

        name = iMarker.getName();
        lat = iMarker.getLocation().latitude;
        lon = iMarker.getLocation().longitude;
        geo = "geo:" + lat + "," + lon;
        intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", name);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    }

    public String getName() {
        return name;
    }

    void addProximityAlert(Context context, LocationManager locationManager) {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                /*&& ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
            // TODO: Consider calling
            return;
        }
        locationManager.addProximityAlert(lat, lon, radius, -1, pendingIntent);
    }

    void removePendingIntent(Context context,LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                /*&& ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
            // TODO: Consider calling
            return;
        }
        locationManager.removeProximityAlert(pendingIntent);
    }

}
