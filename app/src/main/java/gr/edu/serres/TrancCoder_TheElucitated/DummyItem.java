package gr.edu.serres.TrancCoder_TheElucitated;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by GamerX on 19/11/2016.
 */

class ItemNotFoundException extends Exception{

    ItemNotFoundException() {}

    //Constructor that accepts a message
    public ItemNotFoundException(String message)
    {
        super(message);
    }
}

class DummyItem {
    private String name,description;
    private LatLng location;
    private BitmapDescriptor image;
    private GroundOverlay icon;
    private static final double iconSize = 0.0001;
    private boolean showImage;

    DummyItem(String name, String description, LatLng location, int iconResource){
        this.name = name;
        this.description = description;
        this.location = location;
        image = BitmapDescriptorFactory.fromResource(iconResource);
        showImage = false;
    }

    void showImageAndMakeClickable(GoogleMap map){
        icon = map.addGroundOverlay(new GroundOverlayOptions()
                .image(image)
                .positionFromBounds(new LatLngBounds(location,
                        new LatLng(location.latitude+iconSize, location.longitude+iconSize)))
        );
        icon.setClickable(true);
        showImage = true;
    }
    void removeImage(){
        if(showImage){
            icon.remove();
            showImage = false;
        }
    }
    public GroundOverlay getIcon() {
        return icon;
    }

    LatLng getLocation() {
        return location;
    }

    String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
