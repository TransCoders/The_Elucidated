package gr.edu.serres.TrancCoder_TheElucitated.Objects;

/**
 * Created by Damian on 12/14/2016.
 */

public class Item {
    private String name,description,experience,latitude,longitude;

    public Item(){}

    public Item setName(String name){
        this.name = name;
        return this;
    }

    public Item setDescription(String description){
        this.description = description;
        return this;
    }

    public Item setExperience(String experience){
        this.experience = experience;
        return this;
    }

    public Item setLatitude(String latitude){
        this.latitude = latitude;
        return this;
    }

    public Item setLongitude(String longitude){
        this.longitude = longitude;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getExperience() {
        return experience;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
