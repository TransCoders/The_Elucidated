package gr.edu.serres.TrancCoder_TheElucitated.Controllers;

import java.util.HashMap;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;

/**
 * Created by Damian on 12/14/2016.
 */

public class ItemController {
    HashMap<String,Item> itemHashMap;

    public ItemController(){
        itemHashMap = new HashMap<>();
    }

    public void addItem(String name,Item item){
        itemHashMap.put(name,item);
    }

    public Item removeItem(String name){
        Item item =  itemHashMap.get(name);
        itemHashMap.remove(name);
        return item;
    }
}
