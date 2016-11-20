package gr.edu.serres.TrancCoder_TheElucitated;

/**
 * Created by GamerX on 19/11/2016.
 */

public class DummyUser {
    private String email;
    private DummyInventory inventory;
    DummyUser(String email){
        this.email = email;
        inventory = new DummyInventory();
    }

    public DummyInventory getInventory() {
        return inventory;
    }
}
