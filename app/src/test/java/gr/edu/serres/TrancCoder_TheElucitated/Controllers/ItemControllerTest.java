package gr.edu.serres.TrancCoder_TheElucitated.Controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.edu.serres.TrancCoder_TheElucitated.Objects.Item;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by GamerX on 21/12/2016.
 */
public class ItemControllerTest {
    private ItemController itemController;

    @Before
    public void setUp() throws Exception {
        itemController = new ItemController();
    }

    @After
    public void tearDown() throws Exception {
        itemController = null;
    }

    @Test
    public void getItemHashMap() throws Exception {
        assertNotNull(itemController.getItemHashMap());
    }

    @Test
    public void addItem() throws Exception {
        String name = "name";
        Item item = new Item().setName(name);
        itemController.addItem(name,item);
        assertTrue(itemController.getItemHashMap().containsKey(name));
        assertTrue(itemController.getItemHashMap().containsValue(item));
    }

    @Test
    public void removeItem() throws Exception {
        String name = "name";
        Item item = new Item().setName(name);
        itemController.addItem(name,item);
        itemController.removeItem(name);
        assertFalse(itemController.getItemHashMap().containsKey(name));
        assertFalse(itemController.getItemHashMap().containsValue(item));
    }

}