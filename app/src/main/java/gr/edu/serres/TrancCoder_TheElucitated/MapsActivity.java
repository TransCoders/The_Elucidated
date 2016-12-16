package gr.edu.serres.TrancCoder_TheElucitated;

/**
 * Edited by Damian on 11/19/2016.
 */

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Map;

import gr.edu.serres.TrancCoder_TheElucitated.Activities.DialogsActivity;
import gr.edu.serres.TrancCoder_TheElucitated.Adapters.ItemBuilder;
import gr.edu.serres.TrancCoder_TheElucitated.Adapters.MarkerOptionsAdapter;
import gr.edu.serres.TrancCoder_TheElucitated.Adapters.ProximityAdapter;
import gr.edu.serres.TrancCoder_TheElucitated.Adapters.QuestBuilder;
import gr.edu.serres.TrancCoder_TheElucitated.Controllers.ItemController;
import gr.edu.serres.TrancCoder_TheElucitated.Controllers.MarkerController;
import gr.edu.serres.TrancCoder_TheElucitated.Controllers.PendingIntentController;
import gr.edu.serres.TrancCoder_TheElucitated.Controllers.QuestController;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Quest;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;
import gr.edu.serres.TrancCoder_TheElucitated.Services.BackgroundSoundService;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnInfoWindowClickListener {

    boolean mapReady, musicOn;
    Intent backgroundMusic;
    private GoogleMap mMap;
    Button mapTypeBtn, inventoryBtn;
    ToggleButton toggleMusic;
    float MapZoom = 16.5f;
    PopupMenu mapTypeMenu;
    MenuInflater inflater;
    String stateName;
    User user;
    String userEmail;
    /*
     * Location variables
     */
    LatLng mLastLocation;
    /*
     * Proximity variables
     */
    private final String PROX_ALERT = "app.test.PROXIMITY_ALERT";
    private ProximityReceiver proximityReceiver = null;
    private LocationManager locationManager = null;
    private PendingIntentController pendingIntentController;
    private MarkerController markerController;
    private ItemController itemController;
    private QuestController questController;
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
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        stateName = getIntent().getStringExtra("StateName");
        mLastLocation = new LatLng(getIntent().getDoubleExtra("LastLocationLatitude",0),getIntent().getDoubleExtra("LastLocationLongitude",0));
        markerController =  new MarkerController();
        pendingIntentController = new PendingIntentController();
        itemController = new ItemController();
        questController = new QuestController();
        user = new User(userEmail);
        inventoryBtn = (Button) findViewById(R.id.inventory_btn);
        mapReady = false;

        //Create background music -by default music is off
        createBackgroundMusic();
        //create intent for picking item
        mapFragment.getMapAsync(this);

        Button tiltBtn = (Button) findViewById(R.id.tilt_btn);
        tiltBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setCameraOptions();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getInventory().hasItems()) {
                    //Toast.makeText(getApplicationContext(),"first item "+items.get(0).getName(),Toast.LENGTH_LONG).show();
                }
            }
        });
        Toast.makeText(getApplicationContext(),"STATE NAME IS "+stateName,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(musicOn) startService(backgroundMusic);
        //Toast.makeText(getApplicationContext(),"STATE NAME IS "+stateName,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPause() {
        super.onStop();
        if(musicOn) stopService(backgroundMusic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(proximityReceiver);
        }catch (NullPointerException | IllegalArgumentException e){
            e.printStackTrace();
        }
        if(checkPermission()) {
           try {
               for (Object o : pendingIntentController.getPendingIntentHashMap().entrySet()) {
                   locationManager.removeProximityAlert((PendingIntent)((Map.Entry) o).getKey());
               }
           }catch (NullPointerException e){
               e.printStackTrace();
           }
        }
    }

    private void createMarkersForCurrentCounty() {
                createQuestMarkers();
                createItemMarkers();
    }
    private void createQuestMarkers(){

        List<Quest> quests = QuestBuilder.buildQuests(stateName,getApplicationContext());
        for(Quest quest :quests){
            String dialogue = quest.getDialogue();
            Marker marker = mMap.addMarker(MarkerOptionsAdapter.fromQuest(quest));
            marker.setTag(dialogue);
            questController.addQuest(dialogue,quest);
            markerController.addMarker(dialogue,marker);
        }
        String dialogue = questController.getQuestDialogue(0);
        if(dialogue!=null){
            markerController.showMarker(dialogue);
        }
    }

    private void createItemMarkers(){

        proximityReceiver = new ProximityReceiver(markerController);
        IntentFilter intentFilter = new IntentFilter(PROX_ALERT);
        intentFilter.addDataScheme("geo");
        registerReceiver(proximityReceiver, intentFilter);

        List<Item> items = ItemBuilder.buildItems(stateName,getApplicationContext());
        for(Item item :items) {
            String name = item.getName();
            Marker marker = mMap.addMarker(MarkerOptionsAdapter.fromItem(item));
            marker.setTag(item.getExperience());
            markerController.addMarker(name,marker);
            pendingIntentController.addIntent(name,ProximityAdapter.PendingAdapter(item,locationManager,getApplicationContext()));
            itemController.addItem(name,item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        changeMapStyle();// Change Map style to Night mode

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();//My Location and localize button

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //Get Camera Zoom when Camera changes
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                MapZoom =  mMap.getCameraPosition().zoom;
            }
        });

        //Select Map Type
        createPopupMenuForMapType();


        createMarkersForCurrentCounty();

        mMap.setOnInfoWindowClickListener(this);
        mapReady = true;
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        String type = marker.getSnippet();
        int quest_id=0;
        if(type.equals("quest")){
            Toast.makeText(getApplicationContext(), (String) marker.getTag(),Toast.LENGTH_SHORT).show();
            String dialogue = (String) marker.getTag();
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.quest_done));
            String nextQuest = questController.questChooser(dialogue);
            if(nextQuest!=null) {
                markerController.showMarker(nextQuest);
            }
            quest_id = questController.getQuestId(dialogue);
            Intent myIntent = new Intent(MapsActivity.this, DialogsActivity.class);
            myIntent.putExtra("dialogue",getResources().getStringArray(quest_id));
            MapsActivity.this.startActivity(myIntent);
        }else if(type.equals("item")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You want to pick up an "+marker.getTitle()+" ?")
                    .setTitle(marker.getTitle());
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(),"OK button clicked.You got " + marker.getTag()+" Experience",Toast.LENGTH_SHORT).show();
                    PermissionCheck.checkPermission(getApplicationContext());
                    locationManager.removeProximityAlert(pendingIntentController.removeIntent(marker.getTitle()));
                    markerController.remove(marker.getTitle());
                    user.getInventory().addItem(itemController.removeItem(marker.getTitle()));
                }
            });
            builder.setNegativeButton("Leave item",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(),"You leaved "+marker.getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void setCameraOptions(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mLastLocation)
                .zoom(17)
                //.bearing(90)
                .tilt(75)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        return false;
    }

    /**
     * When invoked check permission for FINE or COARSE location
     * @return true if permission granted
     */
    protected boolean checkPermission(){
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
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
    void createPopupMenuForMapType(){
        mapTypeBtn = (Button)findViewById(R.id.options_btn);
        mapTypeMenu = new PopupMenu(MapsActivity.this,mapTypeBtn);
        inflater = mapTypeMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_map_type, mapTypeMenu.getMenu());
        mapTypeMenu.getMenu().findItem(R.id.normal).setChecked(true);
        mapTypeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
