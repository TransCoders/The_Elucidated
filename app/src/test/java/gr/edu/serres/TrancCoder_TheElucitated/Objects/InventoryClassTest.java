package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

/**
 * Created by James Nikolaidis on 12/4/2016.
 */
@RunWith(JUnit4.class)
public class InventoryClassTest extends AndroidTestCase{
    
    private static InventoryClass inventoryClassTest_Var;
    private static ArrayList<String> test_array;
    
    @Before
    public void BeginClass(){
        inventoryClassTest_Var = new InventoryClass();
        
    }
    
    
    @Test
    public void setItemToInventory() throws Exception {
                        //nothing to do
    }

    @Test
    public void returntest_array() throws Exception {

            test_array = new ArrayList<>();
            test_array.add("Magnifying glass");
            test_array.add("Quest Map");
            test_array.add("Lens");
            test_array.add("Handcuffs");
            test_array.add("Binoculars");
            test_array.add("Glasses");
            test_array.add("Mobile phone");
            test_array.add("Hat");
            test_array.add("Anetokoumbo");
            assertEquals("The Object must be the same", test_array, inventoryClassTest_Var.returnItemArray());




    }

    @Test
    public void returntest1_array() throws Exception {
        inventoryClassTest_Var = new InventoryClass("James");
        test_array = new ArrayList<>();
        test_array.add("Magnifying glass");
        test_array.add("Quest Map");
        test_array.add("Lens");
        test_array.add("Handcuffs");
        test_array.add("Binoculars");
        test_array.add("Glasses");
        test_array.add("Mobile phone");
        test_array.add("Hat");
        test_array.add("Camera");
        assertEquals("The Object must be the same", test_array, inventoryClassTest_Var.returnItemArray());




    }
    
    
    
    
    
    
}