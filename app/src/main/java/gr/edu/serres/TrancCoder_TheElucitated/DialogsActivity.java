package gr.edu.serres.TrancCoder_TheElucitated;

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
    private ListView listView;
    private String[] delay;
    private ArrayList<String> oneOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogs_activity);
        if(getIntent().hasExtra("dialogue")){
            delay = getIntent().getExtras().getStringArray("dialogue");
        }else {
            delay = this.getResources().getStringArray(R.array.Victims_home);
        }
        oneOne = new ArrayList<String>();
        //Toast.makeText(getApplicationContext(),String.valueOf(delay.length),Toast.LENGTH_SHORT).show();

        listView = (ListView) this.findViewById(R.id.ListView);

        oneOne.add(delay[counter]);
        ListAdapter adapter = new CustomAdapter(this, oneOne);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                counter++;
                if (counter < delay.length) {
                    oneOne.add(delay[counter]);
                    ListAdapter adapter = new CustomAdapter(getApplicationContext(), oneOne);
                    listView.setAdapter(adapter);
                }
            }
        });
        
    }

}
