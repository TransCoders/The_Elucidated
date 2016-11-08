package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import java.util.ArrayList;

/**
 * Created by Alex on 8/11/2016.
 */

public class InventoryClass {

    public ArrayList<String > ItemArray ;
    public String UserEmail;



    public InventoryClass(String UserMail){
        ItemArray = new ArrayList<>();
        this.UserEmail = UserMail;
        fillArray();
    }
    private void fillArray(){
        ItemArray.add("Magnifying glass");
        ItemArray.add("Mpouzouki");


    }
}
