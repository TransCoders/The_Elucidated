package gr.edu.serres.TrancCoder_TheElucitated.Fragments;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;

import java.util.Iterator;
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
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Quest;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;
import gr.edu.serres.TrancCoder_TheElucitated.PermissionCheck;
import gr.edu.serres.TrancCoder_TheElucitated.ProximityReceiver;
import gr.edu.serres.TrancCoder_TheElucitated.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Damian on 12/16/2016.
 */

public class GMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnInfoWindowClickListener,LocationListener {

    private View v;
    private GoogleMap mMap;

    public Bundle bundle;
    String stateName;
    Double latitude,longitude;

    public final String PROX_ALERT = "app.test.PROXIMITY_ALERT";
    private ProximityReceiver proximityReceiver;
    private LocationManager locationManager = null;
    LatLng mFirstLocation;
    Location mLastLocation=null;
    private PendingIntentController pendingIntentController;
    private MarkerController markerController;
    private ItemController itemController;
    private QuestController questController;

    float MapZoom = 16.5f;

    User user;

    Database_Functions database_functions;
    private float ACCEPTED_DISTANCE_METERS = 150;

    public GMapFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gmap,container,false);
        bundle = getArguments();
        stateName = bundle.getString("StateName");
        latitude = bundle.getDouble("LastLocationLatitude");
        longitude = bundle.getDouble("LastLocationLongitude");
        mFirstLocation = new LatLng(latitude,longitude);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if(PermissionCheck.checkPermission(getActivity())){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        markerController =  new MarkerController();
        pendingIntentController = new PendingIntentController();
        itemController = new ItemController();
        questController = new QuestController();

        database_functions = Database_Functions.getInstance(getActivity(),getActivity());
        user = database_functions.getUserData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(proximityReceiver);
        }catch (NullPointerException | IllegalArgumentException e){
            e.printStackTrace();
        }
        if(PermissionCheck.checkPermission(getActivity())) {
            try {
                for (Object o : pendingIntentController.getPendingIntentHashMap().entrySet()) {
                    locationManager.removeProximityAlert((PendingIntent)((Map.Entry) o).getValue());
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
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
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(mFirstLocation,8);
        mMap.moveCamera(cameraPosition);
        mMap.animateCamera(cameraPosition);

        //Get Camera Zoom when Camera changes
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                MapZoom =  mMap.getCameraPosition().zoom;
            }
        });

        createMarkersForCurrentCounty();

        setUpUser();

        mMap.setOnInfoWindowClickListener(this);

    }


    private void createMarkersForCurrentCounty() {
        createQuestMarkers();
        createItemMarkers();
    }

    private void createQuestMarkers(){

        List<Quest> quests = QuestBuilder.buildQuests(stateName,getActivity());
        for(Quest quest :quests){
            String dialogue = quest.getDialogue();
            Marker marker = mMap.addMarker(MarkerOptionsAdapter.fromQuest(quest));
            marker.setTag(dialogue);
            questController.addQuest(dialogue,quest);
            markerController.addMarker(dialogue,marker);
        }
    }

    private void createItemMarkers(){

        proximityReceiver = new ProximityReceiver(markerController,user.getName());
        IntentFilter intentFilter = new IntentFilter(PROX_ALERT);
        intentFilter.addDataScheme("geo");
        getActivity().registerReceiver(proximityReceiver, intentFilter);

        List<Item> items = ItemBuilder.buildItems(stateName,getActivity());
        for(Item item :items) {
            String name = item.getName();
            Marker marker = mMap.addMarker(MarkerOptionsAdapter.fromItem(item));
            marker.setTag(item.getExperience());
            markerController.addMarker(name,marker);
            pendingIntentController.addIntent(name, ProximityAdapter.PendingAdapter(item,locationManager,getActivity()));
            itemController.addItem(name,item);
        }
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        String type = marker.getSnippet();
        float distance[] = new float[1];
        Location.distanceBetween(mLastLocation.getLatitude(),mLastLocation.getLongitude(),marker.getPosition().latitude,marker.getPosition().longitude,distance);
        int quest_id;
        if(type.equals("quest")){
            if(distance[0]<= ACCEPTED_DISTANCE_METERS) {
                String dialogue = (String) marker.getTag();
                quest_id = questController.getQuestId(dialogue);
                int level = questController.getQuestHashMap().get(dialogue).getLevel();
                if(user.getLevel()==level){
                    user.setLevel(level+1);
                    database_functions.updateUser();
                }
                if(user.getLevel()>=level){
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.quest_done));
                    Intent myIntent = new Intent(getActivity(), DialogsActivity.class);
                    myIntent.putExtra("dialogue", getResources().getStringArray(quest_id));
                    getActivity().startActivity(myIntent);
                }else{
                    Snackbar.make(v,"Hey "+user.getName()+" , your level is too low. You have to complete more quests!",Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(v,"Hey "+user.getName()+" , you're too far from the quest ! 200 meters or less needed to open it!",Snackbar.LENGTH_LONG).show();
            }
        }else if(type.equals("item")){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You want to pick up the "+marker.getTitle()+" ?")
                    .setTitle(marker.getTitle());
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    PermissionCheck.checkPermission(getActivity());
                    locationManager.removeProximityAlert(pendingIntentController.removeIntent(marker.getTitle()));
                    markerController.remove(marker.getTitle());
                    String exp = user.addItemWithExperience(itemController.removeItem(marker.getTitle()));
                    database_functions.updateUser();
                    for (Object o : questController.getQuestHashMap().entrySet()) {
                        final Quest quest = ((Quest) ((Map.Entry) o).getValue());
                        if(Integer.parseInt(quest.getExperience())<=user.getExperience()){
                            if(!quest.isUnlocked()) {
                                markerController.showMarker(quest.getDialogue());
                                questController.unlockQuest(quest.getDialogue());
                                Snackbar.make(v,"You unlocked " + quest.getName() + " Quest",Snackbar.LENGTH_LONG).setAction("",new View.OnClickListener(){

                                    @Override
                                    public void onClick(View view){
                                        float result = displayDistanceToQuest(quest);
                                        Snackbar.make(v,"Distance to quest \""+quest.getName()+"\" is "+ String.valueOf(result) ,Snackbar.LENGTH_LONG).show();
                                    }
                                }).show();
                            }
                        }
                    }
                    Toast.makeText(getActivity(),"Gained " + exp+" Experience",Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Leave item",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Snackbar.make(v,"You leaved the "+marker.getTitle(),Snackbar.LENGTH_LONG).show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public float displayDistanceToQuest(Quest quest){
        float distance[] = new float[1];
        Location.distanceBetween(mLastLocation.getLatitude(),mLastLocation.getLongitude(),Double.parseDouble(quest.getLatitude()),Double.parseDouble(quest.getLongitude()),distance);
        return distance[0];
    }

    public void changeMapStyle(){
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_json));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), " Finding your location  . . . ", Toast.LENGTH_SHORT).show();
        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        int number = user.getInventory().getItemNames().size();
        int realItems = user.getInventory().getItems().size();
        Snackbar.make(v,"User has "+number+" items in his inventory.\nHe actually has "+realItems+" items in his inventory.",Snackbar.LENGTH_LONG).show();
        return false;
    }

    public void enableMyLocation(){
        if (!PermissionCheck.checkPermission(getActivity())){
            Toast.makeText(getActivity(), " GPS Access not granted to the app ", Toast.LENGTH_SHORT).show();
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    public void setCameraOptions(){
        LatLng location;
        if(mLastLocation!=null){
            location = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }else{
            location = mFirstLocation;
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(17)
                //.bearing(90)
                .tilt(75)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_map_settings, menu);

        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.tilt_btn) {
            try {
                setCameraOptions();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return true;
        }else if(id == R.id.map_type){
            registerForContextMenu(v);
            getActivity().openContextMenu(v);
            unregisterForContextMenu(v);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu , View v, ContextMenu.ContextMenuInfo menuInfo){
        getActivity().getMenuInflater().inflate(R.menu.menu_map_type, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
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
                changeMapStyle();
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //Must be called after create items
    void setUpUser(){

        user.getInventory().getItems().clear();
        synchronized (user.getInventory().getItemNames()){
            Iterator<String> it = user.getInventory().getItemNames().listIterator();
            while(it.hasNext()){
                String name = it.next();
                if (itemController.getItemHashMap().containsKey(name)) {
                    if (!user.getInventory().hasItem(name)){
                        user.addItem(itemController.removeItem(name));
                    }
                    markerController.remove(name);
                }
            }
        }
        for (Object o : questController.getQuestHashMap().entrySet()) {
            final Quest quest = ((Quest) ((Map.Entry) o).getValue());
            if(Integer.parseInt(quest.getExperience())<=user.getExperience()){
                if(!quest.isUnlocked()) {
                    markerController.showMarker(quest.getDialogue());
                    questController.unlockQuest(quest.getDialogue());
                }
                if(quest.getLevel()<user.getLevel()){
                    markerController.setIcon(quest.getDialogue(),BitmapDescriptorFactory.fromResource(R.mipmap.quest_done));
                }
            }
        }
        user.setLocation(stateName);
    }
}
