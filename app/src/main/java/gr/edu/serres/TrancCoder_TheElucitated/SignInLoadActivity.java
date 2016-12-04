package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.InventoryClass;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.UsersObject;

/**
 * Created by tasos on 4/12/2016.
 */

public class SignInLoadActivity extends AppCompatActivity {


    private Button continueButton, googleButton;
    private EditText mPassword, mEmail;
    private InventoryClass inventoryClass;
    private String Location;
    private Database_Functions database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_in_load_activity);
        database = Database_Functions.getInstance(getApplicationContext(), this);
        mPassword = (EditText) findViewById(R.id.Password_EditText);
        mEmail = (EditText) findViewById(R.id.Name_Edit_Text);

        continueButton = (Button) findViewById(R.id.button4);
        googleButton = (Button) findViewById(R.id.button5);

        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                database.SetUserInformation(new UsersObject(mPassword.getText().toString(), Location, "0", mEmail.getText().toString()));
                database.SetInventory(new InventoryClass(mEmail.getText().toString()));
                Intent myIntent = new Intent(SignInLoadActivity.this, MapsActivity.class);
                SignInLoadActivity.this.startActivity(myIntent);
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                database.SetUserInformation(new UsersObject(mPassword.getText().toString(), Location, "0", mEmail.getText().toString()));
                database.SetInventory(new InventoryClass(mEmail.getText().toString()));
                Intent myIntent = new Intent(SignInLoadActivity.this, MapsActivity.class);
                SignInLoadActivity.this.startActivity(myIntent);
            }
        });
    }
}