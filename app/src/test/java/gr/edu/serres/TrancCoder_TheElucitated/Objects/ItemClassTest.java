package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import android.test.AndroidTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by James Nikolaidis on 12/4/2016.
 */
@RunWith(JUnit4.class)
public class ItemClassTest  extends AndroidTestCase{
    private ItemClass itemClass;

   //Gets Exception
    @Test
    public void ConstructorTesting(){
        itemClass = new ItemClass();

    }
    //Gets Exception
    @Test
    public void ConstructorTesting1(){
        itemClass = new ItemClass(new String[]{"","","","","",""});

    }

    //Gets Exception
    @Test
    public void ConstructorTesting2(){
        itemClass = new ItemClass(new String[]{"","","","","",""});

    }


    //Gets Exception
    @Test
    public void ConstructorTesting3(){
        itemClass = new ItemClass(new String[]{"Gun","2223","321321"});

    }




}