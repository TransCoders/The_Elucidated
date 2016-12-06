package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alex on 8/11/2016.
 */

public class InventoryClass {

      /*****************************************************\\
     Class varaible decleration
     //******************************************************/
    public List<String> ItemArray ;
    public String UserEmail;


    //CLASS CONSTRUCTOR
    public InventoryClass(){/*An empty Constructor */}
    //-----------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------------------------------------------------------------------




    // CLASS COSTRUCTOR WITH PARAMETERS
    public  InventoryClass(String UserMail){
        ItemArray=  Collections.synchronizedList(new ArrayList<String>());
        this.UserEmail = UserMail;
        fillArray();
    }
    //-----------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------------------------------------------------------------------



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
    //-----------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------------------------------------------------------------------



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



}
