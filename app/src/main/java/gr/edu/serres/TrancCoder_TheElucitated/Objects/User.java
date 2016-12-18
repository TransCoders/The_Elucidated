package gr.edu.serres.TrancCoder_TheElucitated.Objects;

/**
 * Created by James Nikolaidis on 11/6/2016.
 */

public class User{

    public String name,location,email;
    private int experience,level;
    private Inventory inventory;

    public User(){}

    public User(User user){
        this.name = user.getName();
        this.location = user.getLocation();
        this.email = user.getEmail();
        this. experience = user.getExperience();
        this.level = user.getLevel();
        this.inventory = new Inventory(user.getInventory());
    }

    public User(String email){
        this.email = email;
        location = "unknown";
        experience = 0;
        level = 0;
        inventory = new Inventory();
    }

    public void setInventory(Inventory inv){
        inventory = inv;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName(){return this.name;}

    public String getEmail() {
        return email;
    }

    public int getLevel() {
        return level;
    }

    public void addItem(Item item){
        inventory.addItem(item);
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    private String addExperience(String experience) {
        int exp = Integer.parseInt(experience);
        this.experience = this.experience+exp;
        return String.valueOf(experience);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String addItemWithExperience(Item item){
        inventory.addItem(item);
        inventory.getItemNames().add(item.getName());
        return addExperience(item.getExperience());
    }


}


