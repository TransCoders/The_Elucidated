package gr.edu.serres.TrancCoder_TheElucitated.Adapters;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Quest;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by Damian on 12/14/2016.
 */

public class QuestBuilder {

    private QuestBuilder(){}

    public static List<Quest> buildQuests(String stateName, Context context){
        List<Quest> quests = new ArrayList<>();

        Class<R.array> res = R.array.class;
        HashMap<String,TypedArray> typedArrayHashMap = new HashMap<>();
        String[] fields = {"names","latitude","longitude","dialogue"};

        try {
            for(String field : fields){
                Field fieldType = res.getField(stateName.toLowerCase()+"_quest_"+field);
                TypedArray typedArray = context.getResources().obtainTypedArray(fieldType.getInt(null));
                typedArrayHashMap.put(field,typedArray);
            }
            for(int i=0;i<typedArrayHashMap.get("names").length();i++){
                Quest quest = new Quest()
                        .setLevel(i)
                        .setDialogue(typedArrayHashMap.get("dialogue").getString(i))
                        .setIconResource(R.mipmap.quest)
                        .setName(typedArrayHashMap.get("names").getString(i))
                        .setDescription("quest")
                        .setLatitude(typedArrayHashMap.get("latitude").getString(i))
                        .setLongitude(typedArrayHashMap.get("longitude").getString(i));
                quests.add(quest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quests;

    }
}
