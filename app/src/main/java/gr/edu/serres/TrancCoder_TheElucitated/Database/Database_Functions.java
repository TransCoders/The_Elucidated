package gr.edu.serres.TrancCoder_TheElucitated.Database;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.InventoryClass;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.UsersObject;

/**
 * Created by James Nikolaidis on 11/8/2016.
 */

public class Database_Functions {

    private Firebase mRoot,mUsers,mInventory,mItemLocation;
    private FirebaseDatabase database;
    private FirebaseStorage mStorage;
    private static Database_Functions InstanceObject;
    public static int ExpCounter =0;


    private Database_Functions(Context context) {

        Firebase.setAndroidContext(context);
        mRoot = new Firebase("https://the-elusidated-android-app.firebaseio.com/");
        mUsers= new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers");
        mInventory = new Firebase("https://the-elusidated-android-app.firebaseio.com/Inventory");
        mItemLocation = new Firebase("https://the-elusidated-android-app.firebaseio.com/Item Location");

    }


    public static Database_Functions getInstance(Context context){
        if(InstanceObject==null){
            InstanceObject = new Database_Functions(context);
            return InstanceObject;
        }
        return InstanceObject;
    }


    public void SetUserInformation(UsersObject user){
        String name,email,lastname,location;
        mUsers.push().setValue(user);
    }

    public void SetInventory(InventoryClass inventory){
        mInventory.push().setValue(inventory);
    }



    //START Of METHOD TO UPDATE USER EXPERIENCE
    public void Change_User_Experience(final String Experience , String UserEmail  ){
        //Query to find the Child with the given by user email
        Query changeUserExperience  = mUsers.limitToFirst(1).orderByChild("email").equalTo(UserEmail);
        //Triger query Child Listener


        changeUserExperience.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExpCounter = ExpCounter;
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        changeUserExperience.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Gets Child Key
                String key = dataSnapshot.getKey();

                // Create new map for the new added object in the array
                Map<String,Object> ExpMap = new HashMap<String,Object>();
                //Gets UserObject Class object from JSON file
                UsersObject usersObject = dataSnapshot.getValue(UsersObject.class);
                ExpCounter = ExpCounter + Integer.valueOf(Experience);
                //Put a new object to the Map with flag  name Experience and Value  equal to Exp+=experience
                ExpMap.put("Experience",ExpCounter);
                //Create a clone reference from JSON OBject find by the proper Key
                Firebase cloneref = new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers/"+key);
                //Updates Child
                cloneref.child("Experience").setValue(ExpCounter);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}


        });

    }//END OF EXPERIENCE METHOD






}
