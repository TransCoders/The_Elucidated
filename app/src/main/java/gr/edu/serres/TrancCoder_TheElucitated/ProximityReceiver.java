package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Damian on 11/21/2016.
 */

class ProximityReceiver extends BroadcastReceiver {
    GoogleMap mMap;
    MapItems items;

    ProximityReceiver(MapItems items,GoogleMap map){
        this.items = items;
        mMap = map;
    }

    @Override
    public void onReceive(Context arg0, Intent intent) {

        String k = LocationManager.KEY_PROXIMITY_ENTERING;
        boolean state = intent.getBooleanExtra(k, false);

        if (state) {
            Toast.makeText(arg0, "Welcome to my Area", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(arg0, "Thank you for visiting my area", Toast.LENGTH_SHORT).show();
        }

        if (intent.getData() != null) {
            Log.v(TAG, intent.getData().toString());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for(DummyItem item : items.getItems()){
                    if (extras.get("message").equals(item.getName()) && extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                        item.showImageAndMakeClickable(mMap);
                    } else if (extras.get("message").equals(item.getName()) && !extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                        item.removeImage();
                    }
                }
                Log.v("", "Message: " + extras.get("message"));
                Log.v("", "Entering? " + extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING));
            }
        }
    }
}