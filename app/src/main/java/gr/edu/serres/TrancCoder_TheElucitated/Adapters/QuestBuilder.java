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
        String name = "names"; String latitude = "latitude"; String longitude = "longitude"; String dialogue = "dialogue";
        String experience = "experience"; String description = "quest"; boolean unlocked = false;
        String[] fields = { name , latitude , latitude , dialogue, experience };

        try {
            for(String field : fields){
                Field fieldType = res.getField(stateName.toLowerCase()+"_quest_"+field);
                TypedArray typedArray = context.getResources().obtainTypedArray(fieldType.getInt(null));
                typedArrayHashMap.put(field,typedArray);
            }
            for(int i=0;i<typedArrayHashMap.get(name).length();i++){
                Quest quest = new Quest()
                        .setUnlocked(unlocked)
                        .setExperience(typedArrayHashMap.get(experience).getString(i))
                        .setLevel(i)
                        .setDialogue(typedArrayHashMap.get(dialogue).getString(i))
                        .setIconResource(R.mipmap.quest)
                        .setName(typedArrayHashMap.get(name).getString(i))
                        .setDescription(description)
                        .setLatitude(typedArrayHashMap.get(latitude).getString(i))
                        .setLongitude(typedArrayHashMap.get(latitude).getString(i));
                quests.add(quest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quests;

    }
}
