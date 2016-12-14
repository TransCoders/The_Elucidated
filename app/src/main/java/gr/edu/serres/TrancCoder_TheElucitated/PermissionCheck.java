package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Damian on 12/14/2016.
 */

public class PermissionCheck {
    public static boolean checkPermission(Context context){
        return ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
