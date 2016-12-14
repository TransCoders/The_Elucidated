package gr.edu.serres.TrancCoder_TheElucitated.Controllers;

import android.app.PendingIntent;

import java.util.HashMap;

/**
 * Created by Damian on 12/14/2016.
 */

public class PendingIntentController {
    private HashMap<String,PendingIntent> pendingIntentHashMap;

    public PendingIntentController(){
        pendingIntentHashMap = new HashMap<>();
    }

    public void addIntent(String name,PendingIntent pendingIntent){
        pendingIntentHashMap.put(name,pendingIntent);
    }

    public PendingIntent removeIntent(String name){
        PendingIntent pendingIntent = pendingIntentHashMap.get(name);
        pendingIntentHashMap.remove(name);
        return pendingIntent;
    }

    public HashMap<String, PendingIntent> getPendingIntentHashMap() {
        return pendingIntentHashMap;
    }
}
