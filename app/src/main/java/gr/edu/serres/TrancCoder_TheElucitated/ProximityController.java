package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.Context;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GamerX on 22/11/2016.
 */

class ProximityController {

    private List<ProximityPoint> proximityPointList;
    private Context context;
    private LocationManager locationManager;

    ProximityController(Context context, LocationManager locationManager){
        proximityPointList = new ArrayList<>();
        this.context = context;
        this.locationManager = locationManager;
    }
    void createProximityPoint(IMarker item){
        proximityPointList.add(new ProximityPoint(context,item));
    }
    void addProximityAlert(IMarker iMarker){
        for(ProximityPoint proximityPoint : proximityPointList){
            if(proximityPoint.getName().equals(iMarker.getName())){
                proximityPoint.addProximityAlert(context,locationManager);
            }
        }
    }
    void removePendingIntent(IMarker iMarker){
        for(ProximityPoint proximityPoint:proximityPointList){
            if(proximityPoint.getName().equals(iMarker.getName())){
                proximityPoint.removePendingIntent(context,locationManager);
            }
        }
    }

    public List<ProximityPoint> getProximityPointList() {
        return proximityPointList;
    }
}
