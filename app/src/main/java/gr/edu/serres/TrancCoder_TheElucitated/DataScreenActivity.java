package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.InventoryClass;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.ItemClass;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.UsersObject;

/**
 * Created by tasos on 8/11/2016.
 */

public class DataScreenActivity extends AppCompatActivity {


    private Button continueButton;
    private EditText mName, mEmail;
    private InventoryClass inventoryClass;
    private String Location;
    private Database_Functions database;
    private AdapterView<ArrayAdapter<String>> spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.data_screen_activity);
        database = Database_Functions.getInstance(getApplicationContext());
        mName = (EditText) findViewById(R.id.Name_Edit_Text);
        mEmail = (EditText) findViewById(R.id.Email_EditText);

        // Get a reference to the AutoCompleteTextView in the layout
        AdapterView spinner = (AdapterView) findViewById(R.id.spinner);
        // Get the string array
        String[] territories = getResources().getStringArray(R.array.Territories);
        // Create the adapter and set it to the AutoCompleteTextView
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, territories);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Location = String.valueOf(adapterView.getItemAtPosition(i));
                Toast.makeText(getApplicationContext(),Location,Toast.LENGTH_LONG).show();
                database.setItemLocationOnFirebase(getApplicationContext(),Location);
                //
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        continueButton = (Button) findViewById(R.id.button4);

        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                database.SetUserInformation(new UsersObject(mName.getText().toString(), Location, "0", mEmail.getText().toString()));
                database.SetInventory(new InventoryClass(mEmail.getText().toString()));
                Intent myIntent = new Intent(DataScreenActivity.this, MapsActivity.class);
                DataScreenActivity.this.startActivity(myIntent);
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
}
