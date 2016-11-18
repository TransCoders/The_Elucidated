package gr.edu.serres.TrancCoder_TheElucitated;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GamerX on 18/11/2016.
 */

class ItemNotFoundException extends  Exception{
    public ItemNotFoundException() {}

    //Constructor that accepts a message
    public ItemNotFoundException(String message)
    {
        super(message);
    }
}

public class Dummy {
    public static class Item {
        String name;
        LatLng location;
        BitmapDescriptor icon;

        Item(String name, double latitude, double longitude, int resource){
            this.name = name;
            location = new LatLng(latitude,longitude);
            icon = BitmapDescriptorFactory.fromResource(resource);
        }

        public BitmapDescriptor getIcon() {
            return icon;
        }

        public void setIcon(BitmapDescriptor icon) {
            this.icon = icon;
        }

        public LatLng getLocation() {
            return location;
        }

        public void setLocation(LatLng location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static Item getItem(String name, double latitude, double longitude, int resource) {
            return new Item(name, latitude, longitude, resource);
        }
    }
    public class Inventory {
        List<Item> items;

        Inventory(){
            items = new ArrayList();
        }

        public void addItem(Item item){
            items.add(item);
        }

        public List<Item> getItems() {
            return items;
        }

        //Get and Item by searching its name
        public Item getItemByName(String name) throws ItemNotFoundException {
            for (Item item:items){
                if(name.equals(item.getName())) return item;
            }
            //if Item not found
            throw new ItemNotFoundException();
        }
        public LatLng getItemLocationByName(String name){ //throws ItemNotFoundException{
            for (Item item:items){
                if(name.equals(item.getName())) {
                    double latitude = item.getLocation().latitude;
                    double longitude = item.getLocation().longitude;
                    return new LatLng(latitude, longitude);
                }
            }
            return new LatLng(41.075477, 23.553576);
            //throw new ItemNotFoundException();
        }
        public List<String> getItemNames(){
            List<String> names = new ArrayList<>();
            for(Item item:items){
                names.add(item.getName());
            }
                return names;
        }
        public void drawItems(GoogleMap map){
            double smallDistance = 0.0001;
            for(Item item:items){
                GroundOverlay staticIcon = map.addGroundOverlay(new GroundOverlayOptions()
                        .image(item.getIcon())
                        .positionFromBounds(new LatLngBounds(item.getLocation(),new LatLng(item.getLocation().latitude+smallDistance,item.getLocation().longitude+smallDistance)))
                );
            }

        }
    }
    public class User{
        User(){

        }
    }

    Inventory inventory;
    User user;
    Dummy(){
        inventory = new Inventory();
        user = new User();
    }
}
