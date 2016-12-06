package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Damian on 11/21/2016.
 */

class ProximityReceiver extends BroadcastReceiver {
    GoogleMap mMap;
    List<IMarker> markersList;


    ProximityReceiver(GoogleMap map){
        mMap = map;
        markersList = new ArrayList();
    }

    public void addMarker(IMarker iMarker){
        markersList.add(iMarker);
    }

    @Override
    public void onReceive(Context arg0, Intent intent) {

        if (intent.getData() != null) {
            Log.v(TAG, intent.getData().toString());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for(IMarker marker : markersList){
                    if (extras.get("message").equals(marker.getName()) && extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                        Toast.makeText(arg0, "There is an item or more in your area.Check your map"+" <User's Name> "+ "!!!", Toast.LENGTH_SHORT).show();
                        marker.show();
                        //item.showImageAndMakeClickable(mMap);
                    } else if (extras.get("message").equals(marker.getName()) && !extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                        Toast.makeText(arg0, "<User's Name> , You just exited an item's area", Toast.LENGTH_SHORT).show();
                        marker.hide();
                        // item.removeImage();
                    }
                }
            }
        }
    }
}