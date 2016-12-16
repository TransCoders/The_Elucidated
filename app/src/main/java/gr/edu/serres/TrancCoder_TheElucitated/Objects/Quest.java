package gr.edu.serres.TrancCoder_TheElucitated.Objects;

/**
 * Created by Damian on 12/14/2016.
 */

public class Quest {
    private String name,description,latitude,longitude,dialogue,experience;
    private boolean unlocked;

    private int iconResource,level;

    public Quest(){}

    public Quest setName(String name){
        this.name = name;
        return this;
    }

    public Quest setDescription(String description){
        this.description = description;
        return this;
    }

    public Quest setLatitude(String latitude){
        this.latitude = latitude;
        return this;
    }

    public Quest setLongitude(String longitude){
        this.longitude = longitude;
        return this;
    }

    public Quest setDialogue(String dialogue){
        this.dialogue = dialogue;
        return this;
    }

    public Quest setIconResource(int iconResource){
        this.iconResource = iconResource;
        return this;
    }

    public Quest setLevel(int level){
        this.level = level;
        return this;
    }

    public Quest setExperience(String experience){
        this.experience = experience;
        return this;
    }

    public Quest setUnlocked(boolean unlocked){
        this.unlocked = unlocked;
        return this;
    }

    public boolean isUnlocked(){
        return unlocked;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getDialogue(){return dialogue;}

    public String getDescription() {
        return description;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getExperience() { return experience; }

}
