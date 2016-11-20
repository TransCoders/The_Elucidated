package gr.edu.serres.TrancCoder_TheElucitated;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
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
}

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Intent backgroundMusic;
    Spinner pickItemSpinner;
    private GoogleMap mMap;
    private Button mapOptionsButton, mapTypeBtn;
    MapOptions mapOption;
    boolean mapReady = false;
    boolean musicOn;
    String itemSelected;
    LatLng itemSelectedLocation;
    static final LatLng TEI = new LatLng(41.075477, 23.553576);
    float MapZoom = 16.5f;
    private ToggleButton toggleMusic;
    DummyInventory inventoryMap;
    DummyUser user;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        inventoryMap = new DummyInventory();
        user = new DummyUser("myemail@gmail.com");
        pickItemSpinner = (Spinner)findViewById(R.id.pick_item_spinnerr);
        toggleMusic = (ToggleButton)findViewById(R.id.toggle_music) ;
        mapOptionsButton = (Button) findViewById(R.id.map_options);
        mapOption = MapOptions.NORMAL;
        mapOptionsButton.setText(mapOption.toString());
        mapTypeBtn = (Button)findViewById(R.id.options_btn);

        musicOn = false;
        backgroundMusic=new Intent(this, BackgroundSoundService.class);
        toggleMusic.setText(R.string.Music);
        toggleMusic.setTextOff("Music Off");
        toggleMusic.setTextOn("Music On");
        toggleMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(backgroundMusic);
                    musicOn=true;
                } else {
                    stopService(backgroundMusic);
                    musicOn=false;
                }
            }
        });
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
        DummyItem magnifier = new DummyItem("magnifier","A very clean magnifier. Find clues better",new LatLng(41.069131,23.55389),R.mipmap.ic_launcher);
        DummyItem glasses = new DummyItem("glasses","You can't look very far away without them",new LatLng(41.07902,23.553690),R.mipmap.ic_launcher);
        DummyItem handcuffs = new DummyItem("handcuffs","You need these in order to catch the criminal",new LatLng(41.07510,23.552997),R.mipmap.ic_launcher);
        magnifier.showImageAndMakeClickable(mMap);
        glasses.showImageAndMakeClickable(mMap);
        handcuffs.showImageAndMakeClickable(mMap);
        inventoryMap.addItem(magnifier);
        inventoryMap.addItem(glasses);
        inventoryMap.addItem(handcuffs);

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
                    DummyItem itemFound = inventoryMap.getItemByIconId(groundOverlay.getId());
                    //mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(itemFound.getLocation() ,MapZoom));
                    String[] dialogue = {itemFound.getDescription()};
                    Intent myIntent = new Intent( MapsActivity.this, DialogsActivity.class);
                    myIntent.putExtra("dialogue",dialogue);
                    MapsActivity.this.startActivity(myIntent);
                } catch (ItemNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        //Find Items Spinner  and Function
        ArrayAdapter<String> pickItemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,inventoryMap.getNamesOfAllItemsInInventory());
        pickItemSpinner.setAdapter(pickItemAdapter);
        pickItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                itemSelected = String.valueOf(adapterView.getItemAtPosition(i));
                if(!(inventoryMap.getNamesOfAllItemsInInventory().isEmpty())) {
                    try {
                        itemSelectedLocation = inventoryMap.getItem(itemSelected).getLocation();
                        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(itemSelectedLocation ,MapZoom));
                    } catch (ItemNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mapReady = true;

        mapTypeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showPopup(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(musicOn) startService(backgroundMusic);
    }

    @Override
    protected void onPause() {
        super.onStop();
        if(musicOn) stopService(backgroundMusic);
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_map_type,popup.getMenu());
        popup.getMenu().findItem(R.id.normal).setChecked(true);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.satellite:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        return true;
                    case R.id.hybrid:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        return true;
                    case R.id.terrain:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        return true;
                    case R.id.normal:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        return true;
                    default:
                        return MapsActivity.super.onOptionsItemSelected(item);
                }
            }
        });
        popup.show();
    }

}
