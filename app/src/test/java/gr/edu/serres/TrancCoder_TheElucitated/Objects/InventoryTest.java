package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Damian on 12/19/2016.
 */

public class InventoryTest {

    private Inventory inventory ;

    @Before
    public void setUp() throws Exception {
        inventory = new Inventory();
    }

    @After
    public void tearDown() throws Exception {
        inventory = null;
    }

    @Test
    public void createStarterItems() throws Exception {
        List<String> testList = new ArrayList<>();
        testList.add("Magnifying glass");
        testList.add("Handcuffs");
        assertEquals(inventory.getItemNames(),testList);
    }

    @Test
    public void hasItem() throws Exception {
        Item testItem = new Item().setName("test item");
        inventory.addItem(testItem);
        assertTrue(inventory.hasItem(testItem.getName()));
    }

}