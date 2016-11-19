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
        String name,description;
        LatLng location;
        BitmapDescriptor icon;
        GroundOverlay groundOverlay;
        static final double itemImageDistance=0.0001;
        Boolean groundOverlayExists;
        Item(String name, String description, double latitude, double longitude, int iconResource){
            this.name = name;
            this.description = description;
            location = new LatLng(latitude,longitude);
            icon = BitmapDescriptorFactory.fromResource(iconResource);
            groundOverlayExists = false;
        }

        public String getDescription() {
            return description;
        }

        public void makeItemClickable(GoogleMap map){
            if(groundOverlayExists) {
                groundOverlay.setClickable(true);
            }
        }
        public void removeItemFromMap(){
            if(groundOverlayExists) {
                groundOverlay.remove();
                groundOverlayExists = false;
            }
        }

        public GroundOverlay getGroundOverlay() {
            return groundOverlay;
        }

        public void setGroundOverlay(GroundOverlay groundOverlay) {
            this.groundOverlay = groundOverlay;
            groundOverlayExists = true;
        }

        public static double getItemImageDistance() {
            return itemImageDistance;
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

        public static Item createItem(String name, String description, double latitude, double longitude, int resource) {
            return new Item(name, description, latitude, longitude, resource);
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
        public void drawItems(List<Item> items ,GoogleMap map){
            double smallDistance = Item.getItemImageDistance();
            for(Item item:items){
                item.setGroundOverlay(map.addGroundOverlay(new GroundOverlayOptions()
                        .image(item.getIcon())
                        .positionFromBounds(new LatLngBounds(item.getLocation(),new LatLng(item.getLocation().latitude+smallDistance,item.getLocation().longitude+smallDistance)))
                ));
                item.makeItemClickable(map);
            }
        }
        public void setUpMapTest(GoogleMap map){
            items = new ArrayList();
            Item magnifier = new Item("magnifier","Use this to Zoom",41.069131,23.55389,R.mipmap.ic_launcher);
            Item handcuffs = new Item("handcuffs","Black Handcuffs. Use them to catch criminas!",41.07902,23.553690,R.mipmap.ic_launcher);
            Item glasses = new Item("glasses","You can't see very far without them",41.07510,23.552997,R.mipmap.ic_launcher);
            items.add(magnifier);
            items.add(handcuffs);
            items.add(glasses);
            drawItems(items,map);
        }
        public Item getItemFromLocation(List<Item> items,GroundOverlay groundOverlay) throws ItemNotFoundException {
            for(Item item : items){
                if(item.getGroundOverlay().getId().equals(groundOverlay.getId())){
                    return item;
                }
            }
            throw new ItemNotFoundException();
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
