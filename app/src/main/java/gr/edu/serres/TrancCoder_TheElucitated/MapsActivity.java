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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import gr.edu.serres.TrancCoder_TheElucitated.Services.BackgroundSoundService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    String itemSelected;
    boolean mapReady,musicOn;
    Intent backgroundMusic;
    private GoogleMap mMap;
    Button  mapTypeBtn;
    ToggleButton toggleMusic;
    LatLng itemSelectedLocation;
    static final LatLng TEI = new LatLng(41.075477, 23.553576);
    float MapZoom = 16.5f;
    Spinner pickItemSpinner;
    PopupMenu mapTypeMenu;
    MenuInflater inflater;
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
        mapReady = false;
        //create inventory for map -basically items in map
        inventoryMap = new DummyInventory();
        //create dummyuser for testing
        user = new DummyUser("myemail@gmail.com");
        //Create background music -by default music is off
        createBackgroundMusic();
        //create intent for picking item
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

        //Get Camera Zoom when Camera changes
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                MapZoom =  mMap.getCameraPosition().zoom;
            }
        });

        //Add items on Map
        createItemsOnMap();
        //Find Items Spinner  and Function
        //createSpinnerForFindItemInMap();
        //Select Map Type
        createPopupMenuForMapType();
        mapReady = true;
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

    void createBackgroundMusic(){
        toggleMusic = (ToggleButton)findViewById(R.id.toggle_music) ;

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
    }

    void createItemsOnMap(){
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
        //On Item Click Function
        mMap.setOnGroundOverlayClickListener(new GoogleMap.OnGroundOverlayClickListener() {
            @Override
            public void onGroundOverlayClick(GroundOverlay groundOverlay) {
                try {
                    DummyItem itemFound = inventoryMap.getItemByIconId(groundOverlay.getId());
                    //mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(TEI ,MapZoom));
                    Toast.makeText(getApplicationContext(),"Item picked " + itemFound.getName(),Toast.LENGTH_SHORT).show();
                    user.getInventory().addItem(itemFound);
                    inventoryMap.removeItem(itemFound.getName());
                    itemFound.removeImage();
                } catch (ItemNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    void createSpinnerForFindItemInMap(){
        //pickItemSpinner = (Spinner)findViewById(R.id.pick_item_spinnerr);
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
    }

    void createPopupMenuForMapType(){
        mapTypeBtn = (Button)findViewById(R.id.options_btn);
        mapTypeMenu = new PopupMenu(MapsActivity.this,mapTypeBtn);
        inflater = mapTypeMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_map_type, mapTypeMenu.getMenu());
        mapTypeMenu.getMenu().findItem(R.id.normal).setChecked(true);
        mapTypeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mapTypeMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.satellite:
                                if(item.isChecked()){
                                    item.setChecked(false);
                                }else{
                                    item.setChecked(true);
                                    if(mapReady)mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                }
                                return true;
                            case R.id.hybrid:
                                if(item.isChecked()){
                                    item.setChecked(false);
                                }else{
                                    item.setChecked(true);
                                    if(mapReady)mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                }
                                return true;
                            case R.id.terrain:
                                if(item.isChecked()){
                                    item.setChecked(false);
                                }else{
                                    item.setChecked(true);
                                    if(mapReady)mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                }
                                return true;
                            case R.id.normal:
                                if(item.isChecked()){
                                    item.setChecked(false);
                                }else{
                                    item.setChecked(true);
                                    if(mapReady)mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                }
                            default:
                                return MapsActivity.super.onOptionsItemSelected(item);
                        }
                    }
                });
                mapTypeMenu.show();
            }
        });
    }
}
