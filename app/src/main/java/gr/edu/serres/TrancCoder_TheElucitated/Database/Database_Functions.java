package gr.edu.serres.TrancCoder_TheElucitated.Database;

import android.content.Context;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.InventoryClass;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.UsersObject;

/**
 * Created by James Nikolaidis on 11/8/2016.
 */

public class Database_Functions {

    private static Database_Functions InstanceObject;
    private Firebase mRoot, mUsers, mInventory, mItemLocation;
    private FirebaseDatabase database;
    private FirebaseStorage mStorage;


    private Database_Functions(Context context) {

        Firebase.setAndroidContext(context);
        mRoot = new Firebase("https://the-elusidated-android-app.firebaseio.com/");
        mUsers = new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers");
        mInventory = new Firebase("https://the-elusidated-android-app.firebaseio.com/Inventory");
        mItemLocation = new Firebase("https://the-elusidated-android-app.firebaseio.com/Item Location");

    }


    public static Database_Functions getInstance(Context context) {
        if (InstanceObject == null) {
            InstanceObject = new Database_Functions(context);
            return InstanceObject;
        }
        return InstanceObject;
    }


    public void SetUserInformation(UsersObject user) {
        String name, email, lastname, location;
        mUsers.push().setValue(user);
    }

    public void SetInventory(InventoryClass inventory) {
        mInventory.push().setValue(inventory);
    }


}
