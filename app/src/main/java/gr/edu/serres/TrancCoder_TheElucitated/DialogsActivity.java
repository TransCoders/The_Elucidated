package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by tasos on 12/11/2016.
 */

public class DialogsActivity extends AppCompatActivity {

    private static int counter = 0;
    private ListView erwtisiListView;
    private String[] delay;
    private ArrayList<String> erwtisi;
    private ArrayList<String> clonedErwtisi;
    private ListAdapter erwtisiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogs_activity);
        if (getIntent().hasExtra("dialogue")) {
            delay = getIntent().getExtras().getStringArray("dialogue");
        } else {
            delay = this.getResources().getStringArray(R.array.Crime_Scene);
        }

        //Toast.makeText(getApplicationContext(),String.valueOf(delay.length),Toast.LENGTH_SHORT).show();

        erwtisi = new ArrayList<>();


        counter = 0;
        while (counter < delay.length) {
            erwtisi.add(delay[counter]);
            counter = counter + 2;
        }
        counter = 0;
        //Toast.makeText(getApplicationContext(),String.valueOf(erwtisi.size()),Toast.LENGTH_SHORT).show();

        clonedErwtisi = new ArrayList<>();

        clonedErwtisi = (ArrayList<String>) erwtisi.clone();

        erwtisiListView = (ListView) this.findViewById(R.id.ListView);

        erwtisiAdapter = new CustomAdapter(getApplicationContext(), erwtisi);
        erwtisiListView.setAdapter(erwtisiAdapter);

        erwtisiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = adapterView.getPositionForView(view);
                //Toast.makeText(getApplicationContext(), pos + "", Toast.LENGTH_SHORT).show();
                erwtisi.clear();
                erwtisi.add(delay[pos + 1]);
                erwtisiAdapter = new CustomAdapter(getApplicationContext(), erwtisi);
                erwtisiListView.setAdapter(erwtisiAdapter);
                Intent myIntent = new Intent(DialogsActivity.this, MapsActivity.class);
                DialogsActivity.this.startActivity(myIntent);
            }
        });
    }
}