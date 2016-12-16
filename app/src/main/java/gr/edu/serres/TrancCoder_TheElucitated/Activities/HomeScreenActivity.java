package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
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
    private Button newGameButton, loadGameButton, firstStepsButton, testApp;
    private Database_Functions database_functions;
    LocationManager locationManager= null;
    FindCounty findCounty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
      
        setContentView(R.layout.home_screen_activity);


      /*  FirebaseAuth auth = FirebaseAuth.getInstance();
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setProviders(AuthUI.FACEBOOK_PROVIDER)
                .build(),1);
*/

        //database_functions = Database_Functions.getInstance(getApplicationContext(),HomeScreenActivity.this);
        imageView = (ImageView) findViewById(R.id.imageView);

        homeTextView = (TextView) findViewById(R.id.textView);

        newGameButton = (Button) findViewById(R.id.button1);

        loadGameButton = (Button) findViewById(R.id.button2);

        firstStepsButton = (Button) findViewById(R.id.button3);

        testApp = (Button) findViewById(R.id.test_app);


        testApp.setText("Test App");
        testApp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        if(!isGooglePlayServicesAvailable(HomeScreenActivity.this)){
                            buildAlertMessageNoPlayServices();
                        }else {
                            findCounty = new FindCounty(HomeScreenActivity.this, locationManager);
                            findCounty.execute(locationManager);
                        }
                    } else {
                        buildAlertMessageNoGps();
                    }
                }

        });

        newGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent myIntent = new Intent(HomeScreenActivity.this, DataScreenActivity.class);
                HomeScreenActivity.this.startActivity(myIntent);
            }
        });

        loadGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(HomeScreenActivity.this, SignInLoadActivity.class);
                HomeScreenActivity.this.startActivity(myIntent);
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("HomeScreen Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
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