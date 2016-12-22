package gr.edu.serres.TrancCoder_TheElucitated.Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.FindCounty;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by James Nikolaidis on 12/4/2016.
 */

public class Sign_In_With_Email_and_Password_Check {

    private FirebaseAuth mAuth;
    private Database_Functions databaseFunctions;
    private LocationManager locationManager;

    public Sign_In_With_Email_and_Password_Check() {
        mAuth = FirebaseAuth.getInstance();
    }


    public void Sign_In_With_Email_and_Password(String Email, String Password, final Activity activity, final Context context) {

        final boolean[] complete = {true};

        try {

            if (Email != null && Password != null && !Email.matches("") && !Password.matches(""))
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
                                    databaseFunctions = Database_Functions.getInstance(context,activity);
                                    databaseFunctions.getUserProfileAdapter(Email);

                                    locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
                                    FindCounty findCounty;
                                    findCounty = new FindCounty(context, locationManager);
                                    findCounty.execute(locationManager);
                                    activity.finish();
                                }

                                // ...
                            }
                        });
            else {
                throw new NullPointerException();
            }
        } catch (NullPointerException ex) {
        }
    }


    public void LogOut() {
        mAuth.signOut();
    }


}

