package gr.edu.serres.TrancCoder_TheElucitated.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;
import gr.edu.serres.TrancCoder_TheElucitated.R;



public class InventoryFragment extends Fragment {
            private Database_Functions database_functions ;
            private User newUserObject;


    public InventoryFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         newUserObject = new User();
        database_functions = Database_Functions.getInstance(getContext(),getActivity());
        newUserObject=database_functions.getUserData();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View inventoryView =  inflater.inflate(R.layout.fragment_inventory, container, false);
       ListView inventoryListView = (ListView) inventoryView.findViewById(R.id.inventory_fragment);
        Inventory theInvenotry = new Inventory();
        theInvenotry = newUserObject.getInventory();
        List<Item> list  = new ArrayList<>();
        list = theInvenotry.getItems();
        ArrayList<String> newList = new ArrayList<>();
        for(int i=0; i!=list.size(); i++){
            newList.add(list.get(i).getName());
        }

        ListAdapter adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,newList);
        inventoryListView.setAdapter(adapter);


        return  inventoryView;






    }
}
