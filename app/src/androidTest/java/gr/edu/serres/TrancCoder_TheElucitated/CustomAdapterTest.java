package gr.edu.serres.TrancCoder_TheElucitated;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.uiautomator.UiDevice;
import android.test.InstrumentationTestCase;
import android.widget.ListAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

/**
 * Created by James Nikolaidis on 12/4/2016.
 */
@RunWith(JUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CustomAdapterTest extends InstrumentationTestCase {
    private Context testContext;
    private ArrayList<String> test_array;
    private UiDevice mDevice;




    //Check getView with Null ArrayListObject
    @Test
    public void Testin_getView_1(){
        testContext= InstrumentationRegistry.getContext();
        ListAdapter test_adapter = new CustomAdapter(testContext,new ArrayList<String>());

    }

    @Test
    public void getView() throws Exception {

    }

}