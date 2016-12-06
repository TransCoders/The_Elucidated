package gr.edu.serres.TrancCoder_TheElucitated;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Damian on 12/5/2016.
 */

public class MyMarker implements IMarker{

    private GoogleMap map;
    private LatLng location;
    private BitmapDescriptor icon;
    String name;
    String description;
    Marker marker;
    Object tag;

    MyMarker(String name , String description, GoogleMap map, LatLng location, int iconResource, Object tag){
        this.name = name;
        this.description = description;
        this.map = map;
        this.location = location;
        icon = BitmapDescriptorFactory.fromResource(iconResource);
        this.tag = tag;
    }

    MyMarker(String name , String description, GoogleMap map, LatLng location, String iconResource, Object tag){
        this.name = name;
        this.description = description;
        this.map = map;
        this.location = location;
        icon = BitmapDescriptorFactory.fromAsset(iconResource);
        this.tag = tag;
    }

    @Override
    public void add() {
        marker = map.addMarker(new MarkerOptions()
                .icon(icon)
                .position(location)
                .alpha(0.0f)
                .snippet(description)
                .title(name));
        marker.setTag(tag);
    }

    @Override
    public void delete() {
        marker.remove();
    }

    @Override
    public void show() {
        marker.setAlpha(1.0f);
    }

    @Override
    public void hide() {marker.setAlpha(0.0f);}

    @Override
    public LatLng getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return marker.getTitle();
    }

    @Override
    public void showDialog() {
        marker.showInfoWindow();
    }

    @Override
    public String getTag(){
        return (String) marker.getTag();
    }

}
