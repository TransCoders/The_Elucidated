package gr.edu.serres.TrancCoder_TheElucitated.Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by James Nikolaidis on 12/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Database_FunctionsTest extends InstrumentationTestCase{
    private static final String BASIC_SAMPLE_PACKAGE
            = "gr.edu.serres.TrancCoder_TheElucitated";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;
    private static Database_Functions database_functions;
    private User usersObject;

    public  static Context appContext;


    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        appContext= InstrumentationRegistry.getContext();
        final Intent intent = appContext.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        appContext.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);

          ActivityTestRule<Activity> mActivityRule = new ActivityTestRule<>(
                Activity.class);

        database_functions = Database_Functions.getInstance(appContext,mActivityRule.getActivity());
    }

    @Before
    public void Database_FunctionTest(){

    }


    @Test
    public void getInstance() throws Exception {
         ActivityTestRule<Activity> mActivityRule = new ActivityTestRule<>(
                Activity.class);

        database_functions = Database_Functions.getInstance(appContext,mActivityRule.getActivity());
    }

    @Test
    public void setUserInformation() throws Exception {
             usersObject = new User();
                try {
                    database_functions.SetUserInformation(usersObject);
                }catch (NullPointerException exception){
                    mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

                    // Start from the home screen
                    mDevice.pressHome();

                    // Wait for launcher
                    final String launcherPackage = mDevice.getLauncherPackageName();
                    assertThat(launcherPackage, notNullValue());
                    mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                            LAUNCH_TIMEOUT);

                    // Launch the app
                    appContext= InstrumentationRegistry.getContext();
                    final Intent intent = appContext.getPackageManager()
                            .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
                    // Clear out any previous instances
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    appContext.startActivity(intent);

                    // Wait for the app to appear
                    mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                            LAUNCH_TIMEOUT);

                }



    }



    @Test
    public void setInventory1() throws Exception {
        Inventory test_inventory = new Inventory(new User("test_name","greece","0","example@hotmail.com"));
        database_functions.SetInventory(test_inventory);

    }


    @Test
    public void setInventory() throws Exception {
        getInstance();
        Inventory test_inventory = new Inventory();
        database_functions.SetInventory(test_inventory);

    }


    @Test
    public void set_User_Inventory_Item_and_Update() throws Exception {
                database_functions.Set_User_Inventory_Item_and_Update("","","");
                database_functions.Set_User_Inventory_Item_and_Update(null,"testing@email",null);

    }

    @Test
    public void change_User_Experience() throws Exception {

    }

    @Test
    public void change_User_Email() throws Exception {

    }

    @Test
    public void setItemLocationOnFirebase() throws Exception {

    }

}