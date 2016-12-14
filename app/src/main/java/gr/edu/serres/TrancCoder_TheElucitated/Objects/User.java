package gr.edu.serres.TrancCoder_TheElucitated.Objects;

/**
 * Created by James Nikolaidis on 11/6/2016.
 */

public class User {

    public String name,location,Experience,email;
    public Inventory inventory;
    public User(String email){
        this.email = email;
        inventory = new Inventory();
    }

    public User(String ...values){
        this.name = values[0];
        this.location = values[1];
        this.Experience = values[2];
        this.email = values[3];
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void SetUserEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
