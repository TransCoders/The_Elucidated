package gr.edu.serres.TrancCoder_TheElucitated;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by tasos on 12/11/2016.
 */

public class ChooserListViewActivity extends AppCompatActivity {

    private static int counter = 0;
    private String[] delay;
    private ArrayList<String> oneOne;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chooser_listview_activity);

        delay = this.getResources().getStringArray(R.array.Crime_Scene_chooser);

        oneOne = new ArrayList<String>();
        //Toast.makeText(getApplicationContext(),String.valueOf(delay.length),Toast.LENGTH_SHORT).show();

        while (counter < delay.length) {
            oneOne.add(delay[counter]);
            counter++;
        }
        counter = 0;

        listView = (ListView) this.findViewById(R.id.ListView);

        ListAdapter adapter = new CustomAdapter(getApplicationContext(), oneOne);
        listView.setAdapter(adapter);

        /*oneOne.add(delay[counter]);
        ListAdapter adapter = new CustomAdapter(this, oneOne);
        listView.setAdapter(adapter);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ListAdapter adapter = new CustomAdapter(getApplicationContext(), oneOne);
                listView.setAdapter(adapter);
            }
        }, 1000);*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                counter++;
                if (counter < delay.length) {
                    oneOne.add(delay[counter]);
                    ListAdapter adapter = new CustomAdapter(getApplicationContext(), oneOne);
                    listView.setAdapter(adapter);
                }
            }
        });*/
    }
}
