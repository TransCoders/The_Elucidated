package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Fragments.GMapFragment;
import gr.edu.serres.TrancCoder_TheElucitated.Fragments.InventoryFragment;
import gr.edu.serres.TrancCoder_TheElucitated.Fragments.QuestLogFragment;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;
import gr.edu.serres.TrancCoder_TheElucitated.R;
import gr.edu.serres.TrancCoder_TheElucitated.Services.BackgroundSoundService;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String stateName;
    private Bundle bd;
    private FragmentManager fm;

    boolean musicOn;
    Intent backgroundMusic;

    TextView userName ,userEmail;
    ImageView profileImage;

    Database_Functions databaseFunctions;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Gets State Name , LastLocation and starts Game Map by default
        stateName = getIntent().getStringExtra("StateName");
        bd = new Bundle();
        bd.putString("StateName",stateName);
        bd.putDouble("LastLocationLatitude",getIntent().getDoubleExtra("LastLocationLatitude", 0));
        bd.putDouble("LastLocationLongitude",getIntent().getDoubleExtra("LastLocationLongitude", 0));

        fm = getFragmentManager();
        Fragment gMapFragment = new GMapFragment();
        gMapFragment.setArguments(bd);
        fm.beginTransaction().replace(R.id.content_nav, gMapFragment).commit();
        //

        musicOn = false;
        backgroundMusic=new Intent(this, BackgroundSoundService.class);

        //User's profile added to Nav Bar
        View header = navigationView.getHeaderView(0);
        userName = (TextView)header.findViewById(R.id.user_name);
        userEmail = (TextView)header.findViewById(R.id.user_email);
        profileImage = (ImageView)header.findViewById(R.id.imageView);
        databaseFunctions = Database_Functions.getInstance(getApplicationContext(),this);
        user = databaseFunctions.getUserData();

        // Set Nav Bar User Info
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
        profileImage.setImageResource(R.mipmap.detective);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(musicOn) startService(backgroundMusic);
        setTitle(stateName);

    }


    @Override
    protected void onPause() {
        super.onStop();
        if(musicOn) stopService(backgroundMusic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.music_toggle){
            if(!musicOn){
                startService(backgroundMusic);
                item.setIcon(R.mipmap.ic_pause_circle_outline);
                musicOn=true;
            }else{
                stopService(backgroundMusic);
                item.setIcon(R.mipmap.ic_play_circle_outline);
                musicOn=false;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.nav_game_map) {
            if(!item.isChecked()) {
                Fragment fragment = new GMapFragment();
                fragment.setArguments(bd);
                fm.beginTransaction().replace(R.id.content_nav, fragment).commit();
            }
        } else if (id == R.id.nav_inventory) {
            if(!item.isChecked()){
                Fragment fragment = new InventoryFragment();
                fm.beginTransaction().replace(R.id.content_nav, fragment).commit();
            }
        } else if (id == R.id.nav_quests) {
            if(!item.isChecked()){
                Fragment fragment = new QuestLogFragment();
                fm.beginTransaction().replace(R.id.content_nav, fragment).commit();
            }

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            databaseFunctions.logout();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
