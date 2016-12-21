package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Damian on 12/19/2016.
 */
public class UserTestConstructorWithEmail {
    User testUser;
    String testEmail = "android@test.com";

    @Before
    public void setUp() throws Exception {
        testUser = new User(testEmail);
    }

    @After
    public void tearDown() throws Exception {
        testUser = null;
    }

    @Test
    public void createUser() throws  Exception{
        assertEquals(0,testUser.getLevel());
        assertEquals(0,testUser.getExperience());
    }

    @Test
    public void addItemWithExperience() throws Exception {
        Item testItem = new Item();
        String experience = "100";
        testItem.setExperience(experience);
        int exp = Integer.parseInt(experience);
        testUser.addItemWithExperience(testItem);
        assertEquals(testUser.getExperience(),exp);
    }

    @Test
    public void addItemWithExperienceNotDigit() throws Exception { // This Test Should Fail
        Item testItem = new Item();
        String experience = "name";
        testItem.setExperience(experience);
        int exp = Integer.parseInt(experience);
        testUser.addItemWithExperience(testItem);
        assertEquals(testUser.getExperience(),exp);
    }

}