package gr.edu.serres.TrancCoder_TheElucitated;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import gr.edu.serres.TrancCoder_TheElucitated.Authentication.Sign_In_With_Email_And_Password;

/**
 * Created by James Nikolaidis on 12/5/2016.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Sign_In_With_Email_And_PasswordTest {

    String Email="" , Password="" ;

    @Test
    public void signInwithEmailAndPassword() throws Exception {

        Sign_In_With_Email_And_Password details;

        ActivityTestRule<Activity> mActivityRule = new ActivityTestRule<>(
                Activity.class);
        details= new Sign_In_With_Email_And_Password();
        details.SignInwithEmailAndPassword(Email , Password,mActivityRule.getActivity(),InstrumentationRegistry.getContext());


        Email="cat";

        details= new Sign_In_With_Email_And_Password();
        details.SignInwithEmailAndPassword(Email , Password,mActivityRule.getActivity(),InstrumentationRegistry.getContext());

    }

}