package gr.edu.serres.TrancCoder_TheElucitated.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import gr.edu.serres.TrancCoder_TheElucitated.Authentication.Sign_In_With_Email_And_Password;
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by tasos on 4/12/2016.
 */

public class SignInLoadActivity extends AppCompatActivity {

    private static final String TAG = "SignInLoadActivity";
    private Button continueButton, googleButton;
    private EditText mPassword, mEmail;
    private Inventory inventoryClass;
    private String Location;
    private Database_Functions database;
    private Sign_In_With_Email_And_Password sign_in_with_email_and_password;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...


            }
        };









        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                FirebaseAuth.getInstance().signOut();

            }

            @Override
            public void onError(FacebookException error) {

            }
        });














        setContentView(R.layout.sign_in_load_activity);
        database = Database_Functions.getInstance(getApplicationContext(), this);
        mPassword = (EditText) findViewById(R.id.Password_EditText);
        mEmail = (EditText) findViewById(R.id.Name_Edit_Text);

        continueButton = (Button) findViewById(R.id.button4);

        continueButton.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) throws NullPointerException {
                // TODO Auto-generated method stub

                try{


                        if(!mPassword.getText().toString().isEmpty()|| !mPassword.getText().toString().matches("") ||  !mEmail.getText().toString().isEmpty()|| !mEmail.getText().toString().matches("")) {
                            SignIn();
                        }else{throw  new NullPointerException();}

                }catch (NullPointerException exception){
                    System.exit(0);}


            }
        });


    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void sendLoginFacebookData(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "emali"));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInLoadActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }


    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }






    public void SignIn(){
        sign_in_with_email_and_password = new Sign_In_With_Email_And_Password();
        sign_in_with_email_and_password.SignInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString(), this, getApplicationContext());

    }

    public void facebooklogin(View view) {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment

    }
}