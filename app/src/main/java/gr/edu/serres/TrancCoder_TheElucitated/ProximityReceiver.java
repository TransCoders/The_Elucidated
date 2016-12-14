package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import gr.edu.serres.TrancCoder_TheElucitated.Controllers.MarkerController;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Damian on 11/21/2016.
 */

class ProximityReceiver extends BroadcastReceiver {
    MarkerController markerController;


    ProximityReceiver(MarkerController markerController){

        this.markerController = markerController;
    }

    @Override
    public void onReceive(Context arg0, Intent intent) {

        if (intent.getData() != null) {
            Log.v(TAG, intent.getData().toString());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String name = extras.getString("message");
                if(markerController.hasMarker(name)) {
                    if (extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                        Toast.makeText(arg0, "There is an item or more in your area.Check your map" + " <User's Name> " + "!!!", Toast.LENGTH_SHORT).show();
                        markerController.showMarker(name);
                    } else if (!extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                        Toast.makeText(arg0, "<User's Name> , You just exited an item's area", Toast.LENGTH_SHORT).show();
                        markerController.hideMarker(name);
                    }
                }
            }
        }
    }
}