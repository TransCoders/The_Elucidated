package gr.edu.serres.TrancCoder_TheElucitated.Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.MapsActivity;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;

/**
 * Created by James Nikolaidis on 11/27/2016.
 */

public class Email_And_Password_Auth {

    private FirebaseAuth mAuthRef = FirebaseAuth.getInstance();
    private static Context myContext;
    private Database_Functions database;


    public Email_And_Password_Auth(Context context){

        myContext=context;
    }


    public void Create_New_Account_With_Email_Password(final String Email, final  String Password, final Context context, final Activity activity) {
//            Toast.makeText(context,"Here",Toast.LENGTH_SHORT).show();
                database = Database_Functions.getInstance(context,activity);

        try {

            if(Email!=null && Password!=null && !Email.matches("") && !Password.matches("")) {

                mAuthRef.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                Log.d("HEY", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                Toast.makeText(context, "Here3", Toast.LENGTH_SHORT).show();




//
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    User user = new User(Password,"serres", "0", Email);
                                    database.SetUserInformation(user);
                                    database.SetInventory(new Inventory(user));
                                    database.CreateSaveUserState(Email,"FirstQuest","0");
                                   database.Set_User_Inventory_Item_and_Update("Mpaklavas",user.email,"100");
                                    database.SaveUserState(Email,"SecondQuest","100");
                                    Intent myIntent = new Intent(activity, MapsActivity.class);
                                    activity.startActivity(myIntent);

                                }


                            }
                        });
            }else{
                throw new NullPointerException();
            }
        }catch (NullPointerException ex){


        }
    }



}
