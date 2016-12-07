package gr.edu.serres.TrancCoder_TheElucitated;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Damian on 12/5/2016.
 */

public interface IMarker {
    public void add();

    public void delete();

    public void show();

    public void hide();

    public LatLng getLocation();

    public String getName();

    public void showDialog();

    public String getTag();

    public String getDescription();

}
