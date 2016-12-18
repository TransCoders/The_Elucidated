package gr.edu.serres.TrancCoder_TheElucitated.Authentication;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
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

public class Sign_In_With_Email_And_Password {

    private FirebaseAuth mAuth;
    LocationManager locationManager;
    Database_Functions databaseFunctions;
    public Sign_In_With_Email_And_Password(){
        mAuth = FirebaseAuth.getInstance();
    }
    public void SignInWithEmailAndPassword(final String Email, String Password, final Activity activity, final Context context) {
        final boolean[] complete = {true};
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
                    }
                });
    }

    public void LogOut(){
            mAuth.signOut();
        }

}

