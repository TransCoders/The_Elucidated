package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gr.edu.serres.TrancCoder_TheElucitated.IMarker;

/**
 * Created by Alex on 8/11/2016.
 */

public class Inventory {

      /*****************************************************\\
     Class varaible decleration
     //******************************************************/
    public List<String> ItemArray ;
    public String userEmail;
    private List<IMarker> items;


    //CLASS CONSTRUCTOR
    public Inventory(){/*An empty Constructor */}

    public Inventory(User user){
        userEmail = user.getEmail();
        ItemArray=  Collections.synchronizedList(new ArrayList<String>());
       items = Collections.synchronizedList(new ArrayList<IMarker>());
        fillArray();
    }

    // CLASS COSTRUCTOR WITH PARAMETERS
    public Inventory(String UserMail){
        ItemArray=  Collections.synchronizedList(new ArrayList<String>());
        this.userEmail = UserMail;
        fillArray();
        items = Collections.synchronizedList(new ArrayList<IMarker>());
    }

    // CREATE AND FILL AN ARRAY WITH BY GAME DEFAULT OBJECTS
    private void fillArray(){
        ItemArray.add("Magnifying glass");
        ItemArray.add("Quest Map");
        ItemArray.add("Lens");
        ItemArray.add("Handcuffs");
        ItemArray.add("Binoculars");
        ItemArray.add("Glasses");
        ItemArray.add("Mobile phone");
        ItemArray.add("Hat");
        ItemArray.add("Camera");


    }
    // INSERT A NEW OBJECT INTO THE ARRAY
    public void setItemToInventory(String value){}

    //RETURNS THE ARRAYLIST VAR
    public List<String> returnItemArray() throws  NullPointerException{
        try{
            if(!ItemArray.isEmpty()){
                return ItemArray;
            }else
            {throw new NullPointerException();}
        }catch (NullPointerException ex){
            System.exit(0);}


        return null;
    }

    public  void addItem(IMarker item){
        items.add(item);
    }
/*
    public String[] getItemsInfo(){
        String[] info = new String[items.size()*2];
        int i=0;
        for(IMarker item : items){
            info[i++] = item.getName();
            info[i++] = item.getDescription();
        }
        return  info;
    }
*/
   /* public List<String> getItemArray() {
        return ItemArray;
    }*/

    public String getUserEmail() {
        return userEmail;
    }
    public boolean hasItems(){
        return !items.isEmpty();
    }
}
