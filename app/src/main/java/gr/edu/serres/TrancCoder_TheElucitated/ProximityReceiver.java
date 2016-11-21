package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Damian on 11/21/2016.
 */

class ProximityReceiver extends BroadcastReceiver {

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
                if (extras.get("message").equals("SECOND POINT") && extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.tei.setAlpha(1.0f);
                } else if (extras.get("message").equals("glasses") && extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.center.setAlpha(1.0f);
                } else if (extras.get("message").equals("SECOND POINT") && !extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.tei.setAlpha(0.2f);
                } else if (extras.get("message").equals("glasses") && !extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.center.setAlpha(0.2f);
                }
                Log.v("", "Message: " + extras.get("message"));
                Log.v("", "Entering? " + extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING));
            }
        }
    }
}