package gr.edu.serres.TrancCoder_TheElucitated.Controllers;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by Damian on 12/14/2016.
 */

public class MarkerController {
    public HashMap<String,Marker> markerHashMap;

    public MarkerController(){
        markerHashMap = new HashMap<>();
    }

    public void addMarker(String name,Marker marker){
        markerHashMap.put(name,marker);
    }

    public void showMarker(String name){
        markerHashMap.get(name).setVisible(true);
    }

    public void hideMarker(String name){
        markerHashMap.get(name).setVisible(false);
    }

    public void remove(String name){
        markerHashMap.get(name).remove();
        markerHashMap.remove(name);
    }

    public boolean hasMarker(String name){
        return markerHashMap.containsKey(name);
    }

    public void setIcon(String name,BitmapDescriptor icon){
        markerHashMap.get(name).setIcon(icon);
    }
}
