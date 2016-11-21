package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Alex on 8/11/2016.
 */

public class ItemClass {


    private static ItemClass itemClass;
    public String onoma, Lat, Long;
    private ArrayList<ItemClass> ItemArray;


    public  ItemClass(String... values) {
        ItemArray = new ArrayList<>();

        if (values.length != 0) {
            onoma = values[0];
            Lat = values[1];
            Long = values[2];

        }


    }

    public void setValues(Context context) {

        itemClass = new ItemClass("Magnifying glass", "40.323232", "42342323422323", "fsdfsfsdfsf");


    }






}
