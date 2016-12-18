package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.FindCounty;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by tasos on 8/11/2016.
 */

public class HomeScreenActivity extends AppCompatActivity {


    private static final String TAG = "HomeScreenActivity";
    private ImageView imageView;
    private TextView homeTextView;
    private Button newGameButton, loadGameButton, firstStepsButton, testApp;
    private Database_Functions database_functions;
    LocationManager locationManager = null;
    FindCounty findCounty;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...


            }
        };


        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);
        AppEventsLogger.activateApp(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });






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
                    if (!isGooglePlayServicesAvailable(HomeScreenActivity.this)) {
                        buildAlertMessageNoPlayServices();
                    } else {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void sendLoginFacebookData(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "emali"));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(HomeScreenActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
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
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

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