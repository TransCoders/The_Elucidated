package gr.edu.serres.TrancCoder_TheElucitated;

/**
 * Edited by Damian on 11/19/2016.
 */

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

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import gr.edu.serres.TrancCoder_TheElucitated.Services.BackgroundSoundService;
import static com.google.android.gms.internal.zzs.TAG;


import java.io.IOException;
import java.util.List;
import java.util.Locale;



class ProximityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent intent) {

        String k = LocationManager.KEY_PROXIMITY_ENTERING;
        boolean state = intent.getBooleanExtra(k, false);

        if (state) {
            Toast.makeText(arg0, "Welcome to my Area", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(arg0, "Thank you for visiting my area", Toast.LENGTH_SHORT).show();
        }

        if (intent.getData() != null) {
            Log.v(TAG, intent.getData().toString());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (extras.get("message").equals("SECOND POINT") && extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.tei.setAlpha(1.0f);
                } else if (extras.get("message").equals("FIRST POINT") && extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.center.setAlpha(1.0f);
                } else if (extras.get("message").equals("SECOND POINT") && !extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.tei.setAlpha(0.2f);
                } else if (extras.get("message").equals("FIRST POINT") && !extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING)) {
                    MapsActivity.center.setAlpha(0.2f);
                }
                Log.v("", "Message: " + extras.get("message"));
                Log.v("", "Entering? " + extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING));
            }
        }
    }
}


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

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


    /*
     * Location variables
     */
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;



    /*
     * Proximity variables
     */
    private final String PROX_ALERT = "app.test.PROXIMITY_ALERT";
    private ProximityReceiver proximityReceiver = null;
    private LocationManager locationManager = null;
    PendingIntent pendingIntent1 = null;
    PendingIntent pendingIntent2 = null;
    double lat;
    double lon;
    float radius = 100;



    //Test variables
    static Marker tei, center;
    // \ Test variables

    /* Permissions variables
     * used in Permission functions
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (isGooglePlayServicesAvailable(this)) {
            buildGoogleApiClient();
        }

        mapReady = false;
        //create inventory for map -basically items in map
        inventoryMap = new DummyInventory();
        //create dummyuser for testing
        user = new DummyUser("myemail@gmail.com");
        //Create background music -by default music is off
        createBackgroundMusic();
        //create intent for picking item
        mapFragment.getMapAsync(this);


        /////////////////////////////////////
        //Proximity
        lat = 41.087272;
        lon = 23.546726;

        String geo = "geo:" + lat + "," + lon;
        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", "FIRST POINT");
        pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if(checkPermission()) {
            locationManager.addProximityAlert(lat, lon, radius, -1, pendingIntent1);
        }

        lat = 41.076792;
        lon = 23.553650;
        geo = "geo:" + lat + "," + lon;
        intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", "SECOND POINT");

        pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if(checkPermission()) {
            locationManager.addProximityAlert(lat, lon, radius, -1, pendingIntent2);
        }

        proximityReceiver = new ProximityReceiver();
        IntentFilter intentFilter = new IntentFilter(PROX_ALERT);
        intentFilter.addDataScheme("geo");

        registerReceiver(proximityReceiver, intentFilter);

        // \Proximity
        /////////////////////////////////////


    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(musicOn) startService(backgroundMusic);
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onStop();
        if(musicOn) stopService(backgroundMusic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
        unregisterReceiver(proximityReceiver);
        if(checkPermission()) {
            locationManager.removeProximityAlert(pendingIntent1);
            locationManager.removeProximityAlert(pendingIntent2);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);


        if(checkPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (mLastLocation != null) {
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String stateName = addresses.get(0).getLocality();
            Toast.makeText(this, " County = " + stateName, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if(checkPermission()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if(mLastLocation != null){
            //Toast.makeText(this, "Latitude = "+mLastLocation.getLatitude()+" Longitude = "+mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        changeMapStyle();// Change Map style to Night mode

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();//My Location and localize button

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomIn());


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

        // Test markers for proximity
        LatLng TEI = new LatLng(41.076792, 23.553650);
        tei = mMap.addMarker(new MarkerOptions()
                .position(TEI)
                .alpha(0.0f));

        LatLng CENTER = new LatLng(41.087272,23.546726);
        center = mMap.addMarker(new MarkerOptions()
                .position(CENTER)
                .alpha(0.0f));
        mapReady = true;
    }


    public void changeMapStyle(){
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
    }

    public void enableMyLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    /* Shows a Toast with text when
     * MyLocation Button is clicked
     */
    @Override
    public boolean onMyLocationButtonClick(){
        Toast.makeText(this, " Finding your location  . . . ", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * When invoked check permission for FINE or COARSE location
     * @return true if permission granted
     */
    protected boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                /*&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        if(requestCode != LOCATION_PERMISSION_REQUEST_CODE){
            return;
        }

        if(PermissionUtils.isPermissionGranted(permissions, grantResults,Manifest.permission.ACCESS_FINE_LOCATION)){
            enableMyLocation();
        }else{
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments(){
        super.onResumeFragments();
        if(mPermissionDenied){
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError(){
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(),"dialog");
    }

    /**
     * Checks if Google Play services are installed and available
     * @param activity
     * @return true if are installed and available
     */
    public boolean isGooglePlayServicesAvailable(Activity activity){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(resultCode == ConnectionResult.SUCCESS){
            return true;
        }else{
            return false;
        }
    }


    void createBackgroundMusic(){
        toggleMusic = (ToggleButton)findViewById(R.id.toggle_music) ;

        musicOn = false;
        backgroundMusic=new Intent(this, BackgroundSoundService.class);
        toggleMusic.setText("");
        toggleMusic.setTextOn("");
        toggleMusic.setTextOff("");
        toggleMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(backgroundMusic);
                    toggleMusic.setBackgroundResource(R.mipmap.ic_pause_circle_outline);
                    musicOn=true;
                } else {
                    stopService(backgroundMusic);
                    toggleMusic.setBackgroundResource(R.mipmap.ic_play_circle_outline);
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
        ArrayAdapter<String> pickItemAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,inventoryMap.getNamesOfAllItemsInInventory());
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
