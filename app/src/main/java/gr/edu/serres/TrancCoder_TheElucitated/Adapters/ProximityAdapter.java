package gr.edu.serres.TrancCoder_TheElucitated.Adapters;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;
import gr.edu.serres.TrancCoder_TheElucitated.PermissionCheck;

/**
 * Created by Damian on 12/14/2016.
 */

public class ProximityAdapter {
    private final static String PROX_ALERT = "app.test.PROXIMITY_ALERT";
    private final static float radius = 200f;

    private ProximityAdapter(){}

    public static PendingIntent PendingAdapter(Item item,LocationManager locationManager,Context context){
        String lat = item.getLatitude();
        String lon = item.getLongitude();
        String geo = "geo:" + lat + "," + lon;
        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", item.getName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PermissionCheck.checkPermission(context);
        locationManager.addProximityAlert(Double.parseDouble(lat), Double.parseDouble(lon), radius, -1, pendingIntent);
        return pendingIntent;
    }

}
