package gr.edu.serres.TrancCoder_TheElucitated.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by Damian on 12/14/2016.
 */

public class ItemBuilder {

    private ItemBuilder(){}

    public static List<Item> buildItems(String stateName,Context context){
        List<Item> items = new ArrayList<>();

        Class<R.array> res = R.array.class;
        HashMap<String,TypedArray> typedArrayHashMap = new HashMap<>();
        String name = "names"; String experience = "experience"; String latitude = "latitude";
        String longitude = "longitude"; String image = "image";String description = "item";
        String[] fields = { name , experience , latitude , longitude , image };

        try {
            for(String field : fields){
                Field fieldType = res.getField(stateName.toLowerCase()+"_items_"+field);
                TypedArray typedArray = context.getResources().obtainTypedArray(fieldType.getInt(null));
                typedArrayHashMap.put(field,typedArray);
            }
            for(int i=0;i<typedArrayHashMap.get(name).length();i++){
                Item item = new Item()
                        .setName(typedArrayHashMap.get(name).getString(i))
                        .setDescription(description)
                        .setExperience(typedArrayHashMap.get(experience).getString(i))
                        .setLatitude(typedArrayHashMap.get(latitude).getString(i))
                        .setLongitude(typedArrayHashMap.get(longitude).getString(i))
                        .setImage(typedArrayHashMap.get(image).getString(i));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
