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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

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
    Spinner pickItemSpinner;
    private GoogleMap mMap;
    private Button mapOptionsButton;
    MapOptions mapOption;
    boolean mapReady = false;
    String itemSelected;
    LatLng itemSelectedLocation;
    static final LatLng TEI = new LatLng(41.075477, 23.553576);
    float MapZoom = 16.5f;
    private ToggleButton toggleMusic;
    Dummy inventoryUserItem;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        inventoryUserItem = new Dummy();
        pickItemSpinner = (Spinner)findViewById(R.id.pick_item_spinnerr);
        toggleMusic = (ToggleButton)findViewById(R.id.toggle_music) ;
        mapOptionsButton = (Button) findViewById(R.id.map_options);
        mapOption = MapOptions.NORMAL;
        mapOptionsButton.setText(mapOption.toString());
        mapFragment.getMapAsync(this);
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

        //move the Camera to TEI area
        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(TEI , MapZoom) );

        //Change Map Type Button
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
                mMap.setMapType(MapOptions.getOption(mapOption));
            }
        });

        //Set up some items on the map
        inventoryUserItem.inventory.setUpMapTest(mMap);

        //Get Camera Zoom when Camera changes
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                MapZoom =  mMap.getCameraPosition().zoom;
            }
        });

        //On Item Click Function
        mMap.setOnGroundOverlayClickListener(new GoogleMap.OnGroundOverlayClickListener() {
            @Override
            public void onGroundOverlayClick(GroundOverlay groundOverlay) {


                try {
                    Dummy.Item item = inventoryUserItem.inventory.getItemFromLocation(inventoryUserItem.inventory.getItems(),
                            groundOverlay);
                    mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(item.getLocation() ,MapZoom));
                    String[] dialogue = {item.getDescription()};
                    Intent myIntent = new Intent( MapsActivity.this, DialogsActivity.class);
                    myIntent.putExtra("dialogue",dialogue);
                    MapsActivity.this.startActivity(myIntent);
                } catch (ItemNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        //Find Items Spinner  and Function
        ArrayAdapter<String> pickItemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,inventoryUserItem.inventory.getItemNames());
        pickItemSpinner.setAdapter(pickItemAdapter);
        pickItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                itemSelected = String.valueOf(adapterView.getItemAtPosition(i));
                if(!(inventoryUserItem.inventory.getItems().isEmpty())) {
                    itemSelectedLocation = inventoryUserItem.inventory.getItemLocationByName(itemSelected);
                    mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(itemSelectedLocation.latitude,itemSelectedLocation.longitude) ,MapZoom));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mapReady = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic=new Intent(this, BackgroundSoundService.class);
        startService(backgroundMusic);
        toggleMusic.setTextOff("Music Off");
        toggleMusic.setTextOn("Music On");
        toggleMusic.setChecked(true);
        toggleMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(backgroundMusic);
                } else {
                    stopService(backgroundMusic);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onStop();
        stopService(backgroundMusic);
    }
}
