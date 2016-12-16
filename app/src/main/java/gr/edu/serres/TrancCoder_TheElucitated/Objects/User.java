package gr.edu.serres.TrancCoder_TheElucitated.Objects;

/**
 * Created by James Nikolaidis on 11/6/2016.
 */

public class User {

    public String name,location,email;
    private String experience;
    private Inventory inventory;

    public User(){}

    public User(String email){
        this.email = email;
        experience = "0";
        inventory = new Inventory();
    }

    public User(String ...values){
        this.name = values[0];
        this.location = values[1];
        this.experience = values[2];
        this.email = values[3];
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getExperience() {
        return experience;
    }

    public void SetUserEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setInventory(Inventory inv){
        inventory = inv;
    }

    public void addItem(Item item){
        inventory.addItem(item);
    }

    private String addExperience(String experience) {
        int exp = Integer.parseInt(experience);
        int currentExp = Integer.parseInt(this.experience);
        this.experience = String.valueOf(exp+currentExp);
        return experience;
    }

    public String addItemWithExperience(Item item){
        inventory.addItem(item);
        return addExperience(item.getExperience());
    }
}
