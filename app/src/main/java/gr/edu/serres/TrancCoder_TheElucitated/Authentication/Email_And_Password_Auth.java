package gr.edu.serres.TrancCoder_TheElucitated.Authentication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by James Nikolaidis on 11/27/2016.
 */

public class Email_And_Password_Auth {

    private FirebaseAuth mAuthRef = FirebaseAuth.getInstance();
    private static Context myContext;


    public Email_And_Password_Auth(Context context){

        myContext=context;
    }


    public void Create_New_Account_With_Email_Password(String Email, String Password, final Context context, Activity activity){

        mAuthRef.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("HEY", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(context,"failed",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

}
