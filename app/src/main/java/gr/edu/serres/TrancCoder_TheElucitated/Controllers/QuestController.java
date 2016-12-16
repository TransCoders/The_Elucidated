package gr.edu.serres.TrancCoder_TheElucitated.Controllers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Quest;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by Damian on 12/14/2016.
 */

public class QuestController {
    private HashMap<String,Quest> questHashMap;

    public QuestController(){
        questHashMap = new HashMap<>();
    }

    public void addQuest(String name,Quest quest){
        questHashMap.put(name,quest);
    }

    public String questChooser(String dialogue){
        int level = questHashMap.get(dialogue).getLevel();
        for (Object o : questHashMap.entrySet()) {
            Quest quest = (Quest) ((Map.Entry) o).getValue();
            if (quest.getLevel() == level + 1) {
                return quest.getDialogue();
            }
        }
        return null;
    }

    public HashMap<String, Quest> getQuestHashMap() {
        return questHashMap;
    }

    public String getQuestDialogue(int level){
        for (Object o : questHashMap.entrySet()) {
            Quest quest = (Quest) ((Map.Entry) o).getValue();
            if (quest.getLevel() == level) {
                return quest.getDialogue();
            }
        }
        return null;
    }

    public int getQuestId(String dialogue){
        int questId = 0;
        Class<R.array> res = R.array.class;
        Field fieldType;
        try {
            fieldType = res.getField(dialogue);
            questId = fieldType.getInt(null);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return questId;
    }

    public boolean isUnlocked(String name){
        return getQuestHashMap().get(name).isUnlocked();
    }

    public void unlockQuest(String name){
        getQuestHashMap().get(name).setUnlocked(true);
    }
}
