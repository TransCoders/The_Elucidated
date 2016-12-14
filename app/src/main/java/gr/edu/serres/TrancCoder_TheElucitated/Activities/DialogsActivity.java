package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import gr.edu.serres.TrancCoder_TheElucitated.Adapters.CustomAdapter;
import gr.edu.serres.TrancCoder_TheElucitated.R;


/**
 * Created by tasos on 12/11/2016.
 */

public class DialogsActivity extends AppCompatActivity {

    private static int counter = 0;
    private ListView erwtisiListView;
    private String[] dialogue;
    private ArrayList<String> erwtisi;
    private ArrayList<String> apantisi;
    private ListAdapter erwtisiAdapter;
    private boolean erotisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        erotisi = true;
        setContentView(R.layout.dialogs_activity);
        if (getIntent().hasExtra("dialogue")) {
            dialogue = getIntent().getExtras().getStringArray("dialogue");
        } else {
            dialogue = this.getResources().getStringArray(R.array.victims_bar);
        }

        //Toast.makeText(getApplicationContext(),String.valueOf(delay.length),Toast.LENGTH_SHORT).show();

        erwtisi = new ArrayList<>();
        apantisi = new ArrayList<>();

        counter = 0;
        while (counter < dialogue.length) {
            erwtisi.add(dialogue[counter]);
            counter = counter + 2;
        }
        counter = 0;
        //Toast.makeText(getApplicationContext(),String.valueOf(erwtisi.size()),Toast.LENGTH_SHORT).show();

        //clonedErwtisi = new ArrayList<>();
        //clonedErwtisi = (ArrayList<String>) erwtisi.clone();

        erwtisiListView = (ListView) this.findViewById(R.id.ListView);

        erwtisiAdapter = new CustomAdapter(getApplicationContext(), erwtisi);
        erwtisiListView.setAdapter(erwtisiAdapter);

        erwtisiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(erotisi){
                    int pos = adapterView.getPositionForView(view);
                    apantisi.add(dialogue[(pos+pos)+1]);
                    erwtisiAdapter = new CustomAdapter(getApplicationContext(), apantisi);
                    erwtisiListView.setAdapter(erwtisiAdapter);
                    erotisi = false;
                }else{
                    apantisi.clear();
                    erwtisiAdapter = new CustomAdapter(getApplicationContext(), erwtisi);
                    erwtisiListView.setAdapter(erwtisiAdapter);
                    erotisi = true;
                }
                /*int pos = adapterView.getPositionForView(view);
                //Toast.makeText(getApplicationContext(), pos + "", Toast.LENGTH_SHORT).show();

                erwtisi.clear();
                erwtisi.add(dialogue[pos + 1]);
                erwtisiAdapter = new CustomAdapter(getApplicationContext(), erwtisi);
                erwtisiListView.setAdapter(erwtisiAdapter);

                Intent myIntent = new Intent(DialogsActivity.this, MapsActivity.class);
                DialogsActivity.this.startActivity(myIntent);*/
            }
        });
    }
}