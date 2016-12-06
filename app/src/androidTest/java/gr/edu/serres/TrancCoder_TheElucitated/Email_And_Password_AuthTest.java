package gr.edu.serres.TrancCoder_TheElucitated;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import gr.edu.serres.TrancCoder_TheElucitated.Authentication.Email_And_Password_Auth;

/**
 * Created by James Nikolaidis on 12/5/2016.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Email_And_Password_AuthTest {

    Email_And_Password_Auth details;


    @Test
    public void create_New_Account_With_Email_Password() throws Exception {

        String Email="" , Password="";
        ActivityTestRule<Activity> mActivityRule = new ActivityTestRule<>(
                Activity.class);

        details= new Email_And_Password_Auth(InstrumentationRegistry.getContext());
       details.Create_New_Account_With_Email_Password(Email , Password , InstrumentationRegistry.getContext(),mActivityRule.getActivity());

        Email="cat";

        details= new Email_And_Password_Auth(InstrumentationRegistry.getContext());
        details.Create_New_Account_With_Email_Password(Email , Password , InstrumentationRegistry.getContext(),mActivityRule.getActivity());

    }





}