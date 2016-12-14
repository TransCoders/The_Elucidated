package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import gr.edu.serres.TrancCoder_TheElucitated.Adapters.CustomAdapterIinventory;
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.R;


/**
 * Created by James Nikolaidis on 12/12/2016.
 */

public class InventoryActivity extends AppCompatActivity {

    public ListView Inventory;
    public ArrayList<String> items;
    private Inventory userinventory;
    public String[] imageId;
    private Database_Functions mixalis;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_layout);
        preferences = getSharedPreferences("GetEmail",MODE_PRIVATE);
        mixalis = Database_Functions.getInstance(getApplicationContext(),this);
        items = new ArrayList<>();
        userinventory = new Inventory();
        userinventory = mixalis.getUserInventory(preferences.getString("UserEmail","NothingFOund"));
        //items= getResources().getStringArray(R.array.thessaloniki_item);
        items = (ArrayList<String>) userinventory.returnItemArray();
        imageId = getResources().getStringArray(R.array.serres_items_names);
        ListAdapter Adapter = new CustomAdapterIinventory(getApplicationContext(),items,imageId);
        Inventory.setAdapter(Adapter);

    }






}
