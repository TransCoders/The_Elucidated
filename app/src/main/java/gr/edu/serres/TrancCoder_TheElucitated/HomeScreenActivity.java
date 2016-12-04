package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.UsersObject;

/**
 * Created by tasos on 8/11/2016.
 */

public class HomeScreenActivity extends AppCompatActivity {


    private ImageView imageView;
    private TextView homeTextView;
    private Button newGameButton, loadGameButton, firstStepsButton, testApp;
    private Database_Functions database_functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        setContentView(R.layout.home_screen_activity);


      /*  FirebaseAuth auth = FirebaseAuth.getInstance();
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setProviders(
                        AuthUI.FACEBOOK_PROVIDER)
                .build(),1);
*/

        database_functions = Database_Functions.getInstance(getApplicationContext(),HomeScreenActivity.this);
        final UsersObject object = new UsersObject();
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
                Intent myIntent = new Intent(HomeScreenActivity.this, MapsActivity.class);
                HomeScreenActivity.this.startActivity(myIntent);
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
                Intent myIntent = new Intent(HomeScreenActivity.this, ListViewActivity.class);
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

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void facebooklogin(View view) {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment




    }
}