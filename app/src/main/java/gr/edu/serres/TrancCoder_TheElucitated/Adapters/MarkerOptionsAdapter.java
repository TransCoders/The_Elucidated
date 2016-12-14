package gr.edu.serres.TrancCoder_TheElucitated.Adapters;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Quest;

/**
 * Created by Damian on 12/14/2016.
 */

public class MarkerOptionsAdapter {

    private MarkerOptionsAdapter(){}

    public static MarkerOptions fromItem(Item item){
        BitmapDescriptor icon =  BitmapDescriptorFactory.fromAsset(item.getName()+".png");
        LatLng location = new LatLng(Double.parseDouble(item.getLatitude()),Double.parseDouble(item.getLongitude()));
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(icon)
                .position(location)
                .visible(false)
                .snippet(item.getDescription())
                .title(item.getName());
        return markerOptions;
    }

    public static MarkerOptions fromQuest(Quest quest){
        BitmapDescriptor icon =  BitmapDescriptorFactory.fromResource(quest.getIconResource());
        LatLng location = new LatLng(Double.parseDouble(quest.getLatitude()),Double.parseDouble(quest.getLongitude()));
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(icon)
                .position(location)
                .visible(false)
                .snippet(quest.getDescription())
                .title(quest.getName());
        return markerOptions;
    }

}
