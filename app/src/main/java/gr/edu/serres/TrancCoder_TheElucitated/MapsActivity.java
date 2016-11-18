package gr.edu.serres.TrancCoder_TheElucitated;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;

import gr.edu.serres.TrancCoder_TheElucitated.Services.BackgroundSoundService;

enum MapOptions{SATELITE,TERRAIN,HYBRID,NORMAL;
    public static int getOption(MapOptions mapOption){
        switch (mapOption){
            case SATELITE:
                return GoogleMap.MAP_TYPE_SATELLITE;
            case TERRAIN:
                return GoogleMap.MAP_TYPE_TERRAIN;
            case HYBRID:
                return GoogleMap.MAP_TYPE_HYBRID;
            case NORMAL:
                return GoogleMap.MAP_TYPE_NORMAL;
            default:
                return GoogleMap.MAP_TYPE_NORMAL;
        }
    }
};

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Intent backgroundMusic;
    private GoogleMap mMap;
    private Button mapOptionsButton;
    MapOptions mapOption;
    boolean mapReady = false;
    Marker markerTest;
    BitmapDescriptor markerIcon;
    GroundOverlay staticIcon;
    static final LatLng TEI = new LatLng(41.075477, 23.553576);
    //private HashMap<MapOptions,String> mapOptionsStringHashMap;
    //GoogleMap.MAP_TYPE_SATELITE
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapOptionsButton = (Button) findViewById(R.id.map_options);
        mapOption = MapOptions.NORMAL;
        mapOptionsButton.setText(mapOption.toString());
        mapOptionsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch(mapOption){
                    case NORMAL:
                        mapOption = MapOptions.TERRAIN;
                        break;
                    case TERRAIN:
                        mapOption = MapOptions.SATELITE;
                        break;
                    case SATELITE:
                        mapOption = MapOptions.HYBRID;
                        break;
                    case HYBRID:
                        mapOption = MapOptions.NORMAL;
                        break;
                    default:
                        mapOption = MapOptions.NORMAL;
                        break;
                }
                mapOptionsButton.setText(mapOption.toString());
                if(mapReady)mMap.setMapType(MapOptions.getOption(mapOption));
            }
        });
        markerIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ////////////////////////////////
        // Change Map style to Night mode
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }
        // //Change Map style to Night mode
        //////////////////////////////////


        /////////////////////////////////
        // My Location and localize button
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        // //My Locationand localize button
        ////////////////////////////////


        //mMap.animateCamera(CameraUpdateFactory.zoomIn());

        /*markerTest = mMap.addMarker(new MarkerOptions()
                .position(TEI)
                .title("Magnifier")
                .icon(markerIcon));*/
        double smallDistance = 0.0001;
        staticIcon = mMap.addGroundOverlay(new GroundOverlayOptions()
        .image(markerIcon)
        .positionFromBounds(new LatLngBounds(TEI,new LatLng(TEI.latitude+smallDistance,TEI.longitude+smallDistance)))
        );
        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(TEI , 17.0f) );
        mapReady = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic=new Intent(this, BackgroundSoundService.class);
        startService(backgroundMusic);
    }

    @Override
    protected void onPause() {
        super.onStop();
        stopService(backgroundMusic);
    }
}
