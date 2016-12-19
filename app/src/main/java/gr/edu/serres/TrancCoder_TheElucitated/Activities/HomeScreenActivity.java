package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.FindCounty;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by tasos on 8/11/2016.
 */

public class HomeScreenActivity extends AppCompatActivity {


    private ImageView imageView;
    private TextView homeTextView;
    private Button newGameButton, loadGameButton, firstStepsButton;
    LocationManager locationManager= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_screen_activity);

        Configuration config = getResources().getConfiguration();
        if (config.screenWidthDp <= 400) {
            setContentView(R.layout.home_screen_activity_small);
        } else {
            setContentView(R.layout.home_screen_activity);
        }

        AppEventsLogger.activateApp(this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        Firebase.setAndroidContext(getApplicationContext());

        imageView = (ImageView) findViewById(R.id.imageView);

        homeTextView = (TextView) findViewById(R.id.textView);

        newGameButton = (Button) findViewById(R.id.button1);

        loadGameButton = (Button) findViewById(R.id.button2);

        firstStepsButton = (Button) findViewById(R.id.button3);

        newGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if(!isGooglePlayServicesAvailable(HomeScreenActivity.this)){
                        buildAlertMessageNoPlayServices();
                    }else {
                        Intent myIntent = new Intent(HomeScreenActivity.this, DataScreenActivity.class);
                        HomeScreenActivity.this.startActivity(myIntent);
                    }
                } else {
                    buildAlertMessageNoGps();
                }

            }
        });

        loadGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if(!isGooglePlayServicesAvailable(HomeScreenActivity.this)){
                        buildAlertMessageNoPlayServices();
                    }else {
                        Intent myIntent = new Intent(HomeScreenActivity.this, SignInLoadActivity.class);
                        HomeScreenActivity.this.startActivity(myIntent);
                    }
                } else {
                    buildAlertMessageNoGps();
                }

            }
        });

        firstStepsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(HomeScreenActivity.this, DialogsActivity.class);
                HomeScreenActivity.this.startActivity(myIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertMessageNoPlayServices() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Google Play Services Not Available! Please Update It!")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Checks if Google Play services are installed and available
     * @param activity  activity
     * @return true if are installed and available
     */
    public boolean isGooglePlayServicesAvailable(Activity activity){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        return resultCode == ConnectionResult.SUCCESS;
    }
}