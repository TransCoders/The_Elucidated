package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import gr.edu.serres.TrancCoder_TheElucitated.Authentication.Email_And_Password_Auth;
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by tasos on 8/11/2016.
 */

public class DataScreenActivity extends AppCompatActivity {


    private Button continueButton,facebook_login_button;
    private EditText mpassword, mEmail;
    private Inventory inventoryClass;
    private String Location;
    private Database_Functions database;
    private AdapterView<ArrayAdapter<String>> spinner;
    private Email_And_Password_Auth email_and_password_auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_screen_activity);

        Configuration config = getResources().getConfiguration();
        if (config.screenWidthDp <= 400) {
            setContentView(R.layout.data_screen_activity_small);
        } else {
            setContentView(R.layout.data_screen_activity);
        }

        email_and_password_auth = new Email_And_Password_Auth(getApplicationContext());
        mpassword = (EditText) findViewById(R.id.Name_Edit_Text);
        mEmail = (EditText) findViewById(R.id.Email_EditText);
        continueButton = (Button) findViewById(R.id.button4);
        facebook_login_button = (Button) findViewById(R.id.facebook_login_button);




        facebook_login_button.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {
                                                         Intent myIntent = new Intent(DataScreenActivity.this, SignInLoadActivity.class);
                                                         startActivity(myIntent);
                                                     }
                                                 }


        );






        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LogIn();

            }
        });


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
        email_and_password_auth.Create_New_Account_With_Email_Password(mEmail.getText().toString(),mpassword.getText().toString(),getApplicationContext(),DataScreenActivity.this);
    }
}
