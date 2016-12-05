package gr.edu.serres.TrancCoder_TheElucitated;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by tasos on 12/11/2016.
 */

public class DialogsActivity extends AppCompatActivity {

    private static int counter = 0;
    private ListView erwtisiListView;
    private ListView apantisiListView;
    private String[] delay;
    private ArrayList<String> erwtisi;
    private ArrayList<String> apantisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogs_activity);
        if(getIntent().hasExtra("dialogue")){
            delay = getIntent().getExtras().getStringArray("dialogue");
        }else {
            delay = this.getResources().getStringArray(R.array.Crime_Scene);
        }
        erwtisi = new ArrayList<>();
        apantisi = new ArrayList<>();
        //Toast.makeText(getApplicationContext(),String.valueOf(delay.length),Toast.LENGTH_SHORT).show();

        while (counter < delay.length) {
            erwtisi.add(delay[counter]);
            counter = counter + 2;
        }
        counter = 0;

        erwtisiListView = (ListView) this.findViewById(R.id.ListView);

        ListAdapter adapter = new CustomAdapter(getApplicationContext(), erwtisi);
        erwtisiListView.setAdapter(adapter);

        erwtisiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = adapterView.getPositionForView(view);
                Toast.makeText(getApplicationContext(), pos + "", Toast.LENGTH_SHORT).show();
                if (counter < delay.length) {
                    erwtisi.add(delay[counter]);
                    ListAdapter adapter = new CustomAdapter(getApplicationContext(), erwtisi);
                    erwtisiListView.setAdapter(adapter);
                }
            }
        });
    }
}
