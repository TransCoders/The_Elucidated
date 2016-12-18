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
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Inventory;
import gr.edu.serres.TrancCoder_TheElucitated.Objects.User;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by James Nikolaidis on 12/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Database_FunctionsTest extends InstrumentationTestCase {


    private static final String BASIC_SAMPLE_PACKAGE
            = "gr.edu.serres.TrancCoder_TheElucitated";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;
    private static Database_Functions database_functions;
    private User usersObject;

    public static Context appContext;


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
        appContext = InstrumentationRegistry.getContext();
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

        database_functions = Database_Functions.getInstance(appContext, mActivityRule.getActivity());
    }

    @Before
    public void Database_FunctionTest() {

    }


    @Test
    public void getInstance() throws Exception {
        ActivityTestRule<Activity> mActivityRule = new ActivityTestRule<>(
                Activity.class);

        database_functions = Database_Functions.getInstance(appContext, mActivityRule.getActivity());
    }

    @Test
    public void setUserInformation() throws Exception {
        usersObject = new User();
        try {
            //database_functions.SetUserInformation(usersObject);
        } catch (NullPointerException exception) {
            mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

            // Start from the home screen
            mDevice.pressHome();

            // Wait for launcher
            final String launcherPackage = mDevice.getLauncherPackageName();
            assertThat(launcherPackage, notNullValue());
            mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                    LAUNCH_TIMEOUT);

            // Launch the app
            appContext = InstrumentationRegistry.getContext();
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
        //Inventory test_inventory = new Inventory("testing@hotmaail.com");
        //database_functions.SetInventory(test_inventory);

    }


    @Test
    public void setInventory() throws Exception {
        getInstance();
        Inventory test_inventory = new Inventory();
        //database_functions.SetInventory(test_inventory);

    }


    @Test
    public void set_User_Inventory_Item_and_Update() throws Exception {
        database_functions.Set_User_Inventory_Item_and_Update("", "", "");
        database_functions.Set_User_Inventory_Item_and_Update(null, "testing@email", null);
        database_functions.Set_User_Inventory_Item_and_Update(null, null, null);

    }

    @Test
    public void change_User_Experience() throws Exception {
        database_functions.Change_User_Experience("", "");
        database_functions.Change_User_Experience(null, null);
        database_functions.Change_User_Experience("", null);
        database_functions.Change_User_Experience(String.valueOf(1), "");
    }

    @Test
    public void change_User_Email() throws Exception {

        database_functions.Change_User_Email("", "");
        database_functions.Change_User_Email(null, null);
        database_functions.Change_User_Email("", null);
        database_functions.Change_User_Email(String.valueOf(1), "");
    }

    @Test
    public void setItemLocationOnFirebase() throws Exception {
        database_functions.setItemLocationOnFirebase(InstrumentationRegistry.getContext(), "");
        database_functions.setItemLocationOnFirebase(InstrumentationRegistry.getContext(), null);
        database_functions.setItemLocationOnFirebase(InstrumentationRegistry.getContext(), "exampleValue");
    }


    @Test
    public void testGetUserInventory() throws Exception {
        database_functions.getUserInventory("");
        database_functions.getUserInventory(null);
        Inventory inventory = new Inventory();
      //  inventory = database_functions.getUserInventory("example@hotmail.com");
        try {
            List<String> arrayist = new ArrayList<>();
            arrayist = inventory.getItemNames();
            for (int i = 0; i != arrayist.size(); i++) {
                Log.e("ArrayPrint", arrayist.get(i));
            }
        } catch (ArrayIndexOutOfBoundsException exception) {

        }


    }

    @Test
    public void testGetUserLoadQuest() throws Exception {
        database_functions.GetUserLoadQuest("");
        database_functions.GetUserLoadQuest(null);
        database_functions.GetUserLoadQuest(" ");
        String testingQuest = database_functions.GetUserLoadQuest("example@hotmail.com");
        Log.e("QuestTesting", testingQuest);
    }

    @Test
    public void testCreateSaveUserState() throws Exception {
        database_functions.CreateSaveUserState("", "", "");
        database_functions.CreateSaveUserState(null, "", "");
        database_functions.CreateSaveUserState(null, null, null);
        database_functions.CreateSaveUserState("", null, "");
        database_functions.CreateSaveUserState(" ", " ", "100");
        database_functions.CreateSaveUserState("dfggd", "efsdfds", null);
        database_functions.CreateSaveUserState("example@hotmail.com", "", "");

    }

    @Test
    public void testSaveUserState() throws Exception {
        database_functions.SaveUserState("", "", "");
        database_functions.SaveUserState(null, null, null);
        database_functions.SaveUserState(" ", null, null);
        database_functions.SaveUserState(" ", " ", " ");
        database_functions.SaveUserState("example@hotmail.com", "testingQuest", "4");
    }


}

