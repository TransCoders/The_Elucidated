package gr.edu.serres.TrancCoder_TheElucitated.Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
import java.util.regex.Pattern;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.ItemClass;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;

/**
 * Created by James Nikolaidis on 11/8/2016.
 */

public class Database_Functions {
    /*****************************************************\\
     Class variable declaration
     //******************************************************/

    private static Database_Functions InstanceObject;
    private Firebase mRoot,mUsers,mInventory,mItemLocation,mSave;
    private FirebaseDatabase database;
    private FirebaseStorage mStorage;

    public static int ExpCounter =0;
    private static Pattern emailPattern;
    private static Inventory inventory;
    private static User user;
    private static  SharedPreferences preference;

    private Database_Functions() {
        user = null;
        inventory = null;
        mRoot = new Firebase("https://the-elusidated-android-app.firebaseio.com/");
        mUsers= new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers");
        mInventory = new Firebase("https://the-elusidated-android-app.firebaseio.com/Inventory");
        mItemLocation = new Firebase("https://the-elusidated-android-app.firebaseio.com/Item Location");
        mSave = new Firebase("https://the-elusidated-android-app.firebaseio.com/Save");
        emailPattern = Pattern.compile("^[(*^[0-9])\\w]+@(hotmail)|(gmail)+.(com)|(gr)");
    }

    public static Database_Functions getInstance(Context context, Activity activity){
        if(InstanceObject==null){
            //Log.e("HELL","Confffffffffffstructor11111111111");
            InstanceObject = new Database_Functions();
            return InstanceObject;
        }
        return InstanceObject;
    }

    /*public void SetUserInformation(User user){
        try{
            if(user.getExperience()!=null || user.email!=null || user.location!=null || user.getExperience()!=null){
                mUsers.push().setValue(user);
            }else{
                throw new NullPointerException();
            }
        }catch(NullPointerException exception){
            //Intent intent = new Intent(appActivity, HomeScreenActivity.class);
            //appActivity.startActivity(intent);
        }
    }*/

    /*public void SetInventory(Inventory inventory)throws  NullPointerException{
        try{
            if(inventory.getItemNames()!=null || !inventory.getUserEmail().matches("")) {
                mInventory.push().setValue(inventory);
            }else{throw new NullPointerException();}
        }catch(NullPointerException exception){
            //if set System.exit(0) system stops and some test cases will not run in final product we
            //must write System.exit(0);
        }
    }*/
    //-----------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------------------------------------------------------------------
    //START OF METHOD TO UPDATE USER INVENTORY
    public void Set_User_Inventory_Item_and_Update(final String value, final String UserEmail, final String Exp) throws  NullPointerException{

        try{
            if(!value.matches("")|| !UserEmail.matches("")|| !Exp.matches("")|| value!=null || UserEmail!=null || Exp !=null){
                //Query to find the Child with the given by user email
                final Query findProperInventory = mInventory.limitToFirst(1).orderByChild("userEmail").equalTo(UserEmail);
                //Triger query Child Listener
                findProperInventory.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Firebase clone = new Firebase("https://the-elusidated-android-app.firebaseio.com/Inventory/"+String .valueOf(dataSnapshot.getKey()));
                        clone.child("itemNames").setValue(inventory.getItemNames());
                    }
                    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        //if Item has complete change purchase the Item experience
                    }
                    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
                    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                    @Override public void onCancelled(FirebaseError firebaseError) {}
                });
            }else{throw  new NullPointerException();}
        }catch(NullPointerException exception){/*System.exit(0)) */}
    }//END OF METHOD
    //-----------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------------------------------------------------------------------
    //START Of METHOD TO UPDATE USER EXPERIENCE
    public void Change_User_Experience(final String Experience , String UserEmail  )throws NullPointerException{
        //Query to find the Child with the given by user email
        try{
            if(!Experience.matches("") || Experience!=null || !UserEmail.matches("") || UserEmail!=null || !Experience.matches(" ") ){
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
                     //   User usersObject = dataSnapshot.getValue(User.class);
                        ExpCounter = ExpCounter + Integer.valueOf(Experience);
                        //Put a new object to the Map with flag  name Experience and Value  equal to Exp+=experience
                        ExpMap.put("experience",ExpCounter);
                        //Create a clone reference from JSON OBject find by the proper Key
                        Firebase cloneref = new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers/"+key);
                        //Updates Child
                        cloneref.child("experience").setValue(ExpCounter);
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
            }else{ throw new NullPointerException(); }
        }catch(NullPointerException exception){}
    }//END OF EXPERIENCE METHOD
    //START OF METHOD TO UPDATE USER EMAIL
    public void Change_User_Email(String PreviousEmail, final String NewEmail) throws NullPointerException{
        //Query to find the Child with the given by user email
        try{
            if(!PreviousEmail.matches("") || !PreviousEmail.matches(" ") || PreviousEmail!=null || NewEmail!=null || !NewEmail.matches("") || !NewEmail.matches(" ")   ) {
                Query findUser_By_Email = mUsers.limitToFirst(1).orderByChild("email").equalTo(PreviousEmail);
                //Triger query Child Listener
                findUser_By_Email.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //Gets Child Key
                        String key = dataSnapshot.getKey();
                        // Create new map for the new added object in the array
                        Map<String, Object> Updates_User_Email_map = new HashMap<String, Object>();
                        //Put a new object to the Map with flag the child name which we want to update
                        Updates_User_Email_map.put("email", NewEmail);
                        //Create a clone reference from JSON OBject find by the proper Key
                        Firebase newref = new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers/" + key);
                        //Updates Child
                        newref.updateChildren(Updates_User_Email_map);
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
            }else{ new NullPointerException(); }
        }catch(NullPointerException exception){}
    }//END OF METHOD

    //-----------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------------------------------------------------------------------
    public void setItemLocationOnFirebase(final Context context, String Location)throws NullPointerException{
        try{
            Firebase newref;
            Map<String,ItemClass> itemMap = new HashMap<>();
            int counter =1;

            if(!Location.matches("") || !Location.matches(" ") || Location!=null) {
                if (Location.matches("σέρρες") || Location.matches("Νομός Σέρρων")) {
                    newref = new Firebase("https://the-elusidated-android-app.firebaseio.com/Item Location/Νομός Σερρών");
                    itemMap.put("Item" + counter, new ItemClass("chocolate", "41.087022", "23.547429"));
                    counter++;
                    itemMap.put("Item" + counter, new ItemClass("home", "41.090270", "23.54963"));
                    counter++;
                    itemMap.put("Item" + counter, new ItemClass("Accountant", "41.085631", "23.544688"));
                    counter++;
                    itemMap.put("Item" + counter, new ItemClass("Queen_jack_club", "41.092082", "23.558603"));
                    //set new values on the database
                    newref.setValue(itemMap);
                    counter = 1;
                } else if (Location.matches("Νομός Θεσσαλονίκης") || Location.matches("θεσσαλονίκη")) {
                    newref = new Firebase("https://the-elusidated-android-app.firebaseio.com/Item Location/Νομός Θεσσαλονίκης");
                    itemMap.put("Item" + counter, new ItemClass("university", "40.677737", "22.925500"));
                    counter++;
                    itemMap.put("Item" + counter, new ItemClass("home", "40.630389", "22.943000"));
                    counter++;
                    itemMap.put("Item" + counter, new ItemClass("hospital", "40.640063", "22.944419"));
                    //set new values on the database
                    newref.setValue(itemMap);
                    counter = 1;
                } else {
                    //about athens items
                }
            }else{ throw new NullPointerException(); }
        }catch (NullPointerException exception){}
    }

    public void getUserInventory(String Useremail){
        Query getUserQuery = mInventory.limitToFirst(1).orderByChild("userEmail").equalTo(Useremail);
        getUserQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                inventory = dataSnapshot.getValue(Inventory.class);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    public String GetUserLoadQuest(String UserEmail)throws NullPointerException{
        try{
            if(!UserEmail.matches("") || !UserEmail.matches(" ") || UserEmail!=null)  {
                final String[] quest2 = new String[1];
                Query getLoadUserQuest = mSave.limitToFirst(1).orderByChild("UserEmail").equalTo(UserEmail);
                getLoadUserQuest.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String quest = dataSnapshot.child("Quest").getValue(String.class);
                        quest2[0]=quest;
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
                if(quest2[0]!=null || !quest2[0].isEmpty()){
                    return quest2[0];
                }else{
                    System.exit(0);
                    return null;
                }
            }else{throw new NullPointerException();}
        }catch (NullPointerException exception){
            return  null;
        }
    }

    public void CreateSaveUserState(String UserEmail, final String CurrentQuest, String Exp)throws NullPointerException{
        try{
            if(!UserEmail.matches("") || UserEmail.matches(" ") || UserEmail!=null || !CurrentQuest.matches("") || !CurrentQuest.matches(" ") || CurrentQuest!=null|| !Exp.matches("") || !Exp.matches(" ") || Exp!=null) {
                Map<String, String> mMap = new HashMap<>();
                mMap.put("Quest", CurrentQuest);
                mMap.put("UserEmail", UserEmail);
                mSave.push().setValue(mMap);
            }else{throw new NullPointerException();}
        }catch (NullPointerException exceptio){}
    }

    public void SaveUserState(String UserEmail, final String CurrentQuest, String Exp)throws  NullPointerException{
        try{
            if(!UserEmail.matches("") || UserEmail.matches(" ") || UserEmail!=null || !CurrentQuest.matches("") || !CurrentQuest.matches(" ") || CurrentQuest!=null|| !Exp.matches("") || !Exp.matches(" ") || Exp!=null){
                Query saveQuest = mSave.limitToFirst(1).orderByChild("UserEmail").equalTo(UserEmail);
                saveQuest.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String key = dataSnapshot.getKey();
                        Firebase newRoot = new Firebase("https://the-elusidated-android-app.firebaseio.com/Save/"+key).child("Quest");
                        newRoot.setValue(CurrentQuest);
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

                Change_User_Experience_Update(Exp,UserEmail);
            }else{ throw new NullPointerException(); }
        }catch ( NullPointerException exception ){}
    }
    //USE THIS FUNCTION TO GET USER INVENTORY OBJECT
    public Inventory getInv(){
    return inventory;
}

    //START Of METHOD TO UPDATE USER EXPERIENCE
    public void Change_User_Experience_Update(final String Experience , String UserEmail  )throws NullPointerException {

        //Query to find the Child with the given by user email
        try {
            if (!Experience.matches("") || Experience != null || !UserEmail.matches("") || UserEmail != null || !Experience.matches(" ")) {
                Query changeUserExperience = mUsers.limitToFirst(1).orderByChild("email").equalTo(UserEmail);
                //Triger query Child Listener
                changeUserExperience.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ExpCounter = ExpCounter;
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                changeUserExperience.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        //Gets Child Key
                        String key = dataSnapshot.getKey();
                        //Create a clone reference from JSON OBject find by the proper Key
                        Firebase cloneref = new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers/" + key);
                        //Updates Child
                        cloneref.child("experience").setValue(Experience);
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException exception) {}
    }


     /*
        Οι παρακάτω σειρές χρειάζονται για να δουλέψει το παιχνίδι
        ΜΗΝ ΤΗΣ ΣΒΗΣΕΙ ΚΑΝΕΙΣ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */

    public void getUserProfileAdapter(final String Useremail){
        Query findUser_By_Email = mUsers.limitToFirst(1).orderByChild("email").equalTo(Useremail);
        //Triger query Child Listener
        findUser_By_Email.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = dataSnapshot.getValue(User.class);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    //USE THIS FUNCTION TO GET USER DATA OBJECT
    public User getUserData(){
        return user;
    }


    public void logout(){
        user = null;
        inventory =null;
    }
    public void createUserFacebook(User user){

    }
    public void updateUser(){
        final Query findProperInventory = mUsers.limitToFirst(1).orderByChild("email").equalTo(user.getEmail());
        findProperInventory.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Firebase clone = new Firebase("https://the-elusidated-android-app.firebaseio.com/AppUsers/"+String .valueOf(dataSnapshot.getKey()));
                User tempUser = new User(user);
                clone.setValue(tempUser);
            }
            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override public void onCancelled(FirebaseError firebaseError) {}
        });
    }
    public void createUser(User user){
        mUsers.push().setValue(user);
    }
}
