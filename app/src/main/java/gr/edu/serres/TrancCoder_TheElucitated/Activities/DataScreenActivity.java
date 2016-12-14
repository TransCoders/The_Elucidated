package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import gr.edu.serres.TrancCoder_TheElucitated.Authentication.Email_And_Password_Auth;
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by tasos on 8/11/2016.
 */

public class DataScreenActivity extends AppCompatActivity {


    private Button continueButton;
    private EditText mpassword, mEmail;
    private Inventory inventoryClass;
    private String Location;
    private Database_Functions database;
    private AdapterView<ArrayAdapter<String>> spinner;
    private Email_And_Password_Auth email_and_password_auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email_and_password_auth = new Email_And_Password_Auth(getApplicationContext());
        setContentView(R.layout.data_screen_activity);
        database = Database_Functions.getInstance(getApplicationContext(),this);
        mpassword = (EditText) findViewById(R.id.Name_Edit_Text);
        mEmail = (EditText) findViewById(R.id.Email_EditText);

        // Get a reference to the AutoCompleteTextView in the layout

        // Get the string array
        String[] territories = getResources().getStringArray(R.array.Territories);
        // Create the adapter and set it to the AutoCompleteTextView



        continueButton = (Button) findViewById(R.id.button4);

        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LogIn();

            }
        });


    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("DataScreen Page") // TODO: Define a title for the content shown.
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


    public void  LogIn(){
        email_and_password_auth.Create_New_Account_With_Email_Password(mEmail.getText().toString(),mpassword.getText().toString(),getApplicationContext(),this);
    }
}
