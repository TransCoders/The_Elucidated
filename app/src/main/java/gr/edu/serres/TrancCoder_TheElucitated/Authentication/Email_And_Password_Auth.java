package gr.edu.serres.TrancCoder_TheElucitated.Authentication;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.FindCounty;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;

/**
 * Created by James Nikolaidis on 11/27/2016.
 */

public class Email_And_Password_Auth {

    private FirebaseAuth mAuthRef = FirebaseAuth.getInstance();
    private Database_Functions database;

    // Async Task variables
    LocationManager locationManager;

    public Email_And_Password_Auth(Context context){
    }

    public void Create_New_Account_With_Email_Password(final String Email, final  String Password, final Context context, final Activity activity) {
                mAuthRef.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                database = Database_Functions.getInstance(context,activity);
                                //Log.d("HEY", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Account created successfully.", Toast.LENGTH_SHORT).show();
                                    User user = new User(Email);
                                    database.createUser(user);
                                    database.getUserProfileAdapter(Email);

                                    locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
                                    FindCounty findCounty;
                                    findCounty = new FindCounty(context, locationManager);
                                    findCounty.execute(locationManager);
                                    activity.finish();
                                }
                            }
                        });

    }
}
