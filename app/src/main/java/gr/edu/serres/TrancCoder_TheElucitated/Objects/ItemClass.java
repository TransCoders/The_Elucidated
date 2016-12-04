package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import java.util.ArrayList;

/**
 * Created by Alex on 8/11/2016.
 */

public class ItemClass {


    private static ItemClass itemClass;
    public String onoma, Lat, Long;
    private ArrayList<ItemClass> ItemArray;


    public  ItemClass(String... values)throws NullPointerException {
        ItemArray = new ArrayList<>();

    try {
        if (values.length <= 3) {
            try {
                    if (values.length != 0 && !values[0].matches("")&& !values[1].matches("")&& !values[2].matches("")) {
                        onoma = values[0];
                        Lat = values[1];
                        Long = values[2];

                    } else {
                        throw new NullPointerException();
                    }
                } catch (NullPointerException exception) {
                        System.exit(1);
                }



        }/*If values that user gave was more than required */else {throw  new IndexOutOfBoundsException();}
    }catch(IndexOutOfBoundsException exception){
            System.exit(0);
    }


    }//***************** END OF METHOD




}
