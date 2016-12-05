package gr.edu.serres.TrancCoder_TheElucitated;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.login.widget.LoginButton;

import gr.edu.serres.TrancCoder_TheElucitated.Authentication.Sign_In_With_Email_And_Password;
import gr.edu.serres.TrancCoder_TheElucitated.Database.Database_Functions;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.InventoryClass;

/**
 * Created by tasos on 4/12/2016.
 */

public class SignInLoadActivity extends AppCompatActivity {


    private Button continueButton, googleButton;
    private EditText mPassword, mEmail;
    private InventoryClass inventoryClass;
    private String Location;
    private Database_Functions database;
    private Sign_In_With_Email_And_Password sign_in_with_email_and_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void SignIn(){
        sign_in_with_email_and_password = new Sign_In_With_Email_And_Password();
        sign_in_with_email_and_password.SignInwithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString(),this,getApplicationContext());

    }

    public void facebooklogin(View view) {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment

    }
}