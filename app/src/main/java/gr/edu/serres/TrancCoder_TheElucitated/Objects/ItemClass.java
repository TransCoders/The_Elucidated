package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Alex on 8/11/2016.
 */

public class ItemClass {


    private static ItemClass itemClass;
    private String onoma, Lat, Long, perioxi;
    private ArrayList<ItemClass> ItemArray;


    private ItemClass(Context context, String... values) {
        ItemArray = new ArrayList<>();

        if (values.length != 0) {
            onoma = values[0];
            Lat = values[1];
            Long = values[2];
            perioxi = values[3];
        }

    }

    public void setValues(Context context) {

        itemClass = new ItemClass(context, "Magnifying glass", "40.323232", "42342323422323", "fsdfsfsdfsf");

    }
}
