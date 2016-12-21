package gr.edu.serres.TrancCoder_TheElucitated.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by GamerX on 21/12/2016.
 */
public class ItemTest {
    private Item itemTest;
    @Before
    public void setUp() throws Exception {
        itemTest = new Item();
    }

    @After
    public void tearDown() throws Exception {
        itemTest = null;
    }

    @Test
    public void SetAndGetName() throws Exception {
        String name = "name";
        assertEquals(name,itemTest.setName(name).getName());
    }

    @Test
    public void SetAndGetDescription() throws Exception {
        String description = "description";
        assertEquals(description,itemTest.setDescription(description).getDescription());
    }

    @Test
    public void SetAndGetExperience() throws Exception {
        String experience = "experience";
        assertEquals(experience,itemTest.setExperience(experience).getExperience());
    }

    @Test
    public void SetAndGetLatitude() throws Exception {
        String latitude = "24.1231";
        assertEquals(latitude,itemTest.setLatitude(latitude).getLatitude());
    }

    @Test
    public void setLongitude() throws Exception {
        String longitude = "43.1231";
        assertEquals(longitude,itemTest.setLongitude(longitude).getLongitude());
    }

    @Test
    public void setImage() throws Exception {
        String image = "image.png";
        assertEquals(image,itemTest.setImage(image).getImage());
    }

    @Test
    public void latitudeAcceptsLetters() throws Exception{
        String latitude = "latitude";
        try {
            Double.parseDouble(latitude);
        }catch (Exception e){
            fail();
        }
        itemTest.setLatitude(latitude);
        //fail();
    }

    @Test
    public void longitudeAcceptsLetters() throws Exception{
        String longitude = "latitude";
        itemTest.setLongitude(longitude);
        fail();
    }

    @Test
    public void imageWithNoExtension() throws Exception{
        String image = "image";
        itemTest.setImage(image);
        fail();
    }

}