package gr.edu.serres.TrancCoder_TheElucitated;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by tasos on 8/11/2016.
 */

public class DataScreenActivity extends AppCompatActivity {


    private Button continueButton;
    private AdapterView<ArrayAdapter<String>> spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.data_screen_activity);

        // Get a reference to the AutoCompleteTextView in the layout
        AdapterView spinner = (AdapterView) findViewById(R.id.spinner);
        // Get the string array
        String[] territories = getResources().getStringArray(R.array.Territories);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, territories);
        spinner.setAdapter(adapter);


        continueButton = (Button) findViewById(R.id.button4);

        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
