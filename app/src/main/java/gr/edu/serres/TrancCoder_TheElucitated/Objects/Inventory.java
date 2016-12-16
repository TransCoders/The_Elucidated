package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Alex on 8/11/2016.
 */

public class Inventory {

      /*****************************************************\\
     Class varaible decleration
     //******************************************************/
    private List<String> itemNames;
    private String userEmail;
    private List<Item> items;

    public Inventory(){
        items = Collections.synchronizedList(new ArrayList<Item>());
    }

    public Inventory(List<String> testingList , String Usermail){
       this.itemNames = testingList;
        this.userEmail = Usermail;
    }
    public Inventory(String UserMail){
        itemNames =  Collections.synchronizedList(new ArrayList<String>());
        this.userEmail = UserMail;
        fillArray();
        items = Collections.synchronizedList(new ArrayList<Item>());
    }

    // CREATE AND FILL AN ARRAY WITH BY GAME DEFAULT OBJECTS
    private void fillArray(){
        itemNames.add("Magnifying glass");
        itemNames.add("Quest Map");
        itemNames.add("Lens");
        itemNames.add("Handcuffs");
        itemNames.add("Binoculars");
        itemNames.add("Glasses");
        itemNames.add("Mobile phone");
        itemNames.add("Hat");
        itemNames.add("Camera");


    }
    // INSERT A NEW OBJECT INTO THE ARRAY
    public void setItemToInventory(String value){}

    public List<String> getItemNames() {
        return itemNames;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean hasItems(){
        return !items.isEmpty();
    }

}
