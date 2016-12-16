package gr.edu.serres.TrancCoder_TheElucitated.Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.MapsActivity;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by James Nikolaidis on 12/4/2016.
 */

public class Sign_In_With_Email_And_Password {

    private FirebaseAuth mAuth;
    private Database_Functions database;
    private SharedPreferences preferences ;
    private SharedPreferences.Editor editor;
    public Sign_In_With_Email_And_Password(){
        mAuth = FirebaseAuth.getInstance();
    }


    public void SignInWithEmailAndPassword(final String Email, String Password, final Activity activity, final Context context) {

        final boolean[] complete = {true};
        editor = preferences.edit();
        editor.putString("UserEmail",Email);
        editor.commit();
        database = Database_Functions.getInstance(context,activity);

        try {

            if(Email!=null && Password!=null && !Email.matches("") && !Password.matches(""))
              mAuth.signInWithEmailAndPassword(Email, Password)

                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:Success", task.getException());
                                Toast.makeText(context, "Sign in Success", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(activity, MapsActivity.class);
                                database.getUserInventory(Email);
                                database.getUserProfileAdapter(Email);
                                activity.startActivity(myIntent);

                            }

                            // ...
                        }
                    });
            else
            {
                throw new NullPointerException();
            }
        }catch (NullPointerException ex){
        }
    }


        public void LogOut(){
            mAuth.signOut();
        }




}

