package gr.edu.serres.TrancCoder_TheElucitated.Software;

import android.app.Activity;

import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by James Nikolaidis on 11/21/2016.
 */

public class Software_functions {



    public String[] set_item_map(String stringArray, Activity activity){
        String[] returnarray;

        switch(stringArray){
            case "Thessaloniki":
                returnarray = activity.getResources().getStringArray(R.array.thessaloniki_item);
                break;
            case "Athena" :
                returnarray = activity.getResources().getStringArray(R.array.athena_item);
                break;
            default :
                returnarray = activity.getResources().getStringArray(R.array.serres_items_names);
                break;
        }

        return returnarray;
    }


}
