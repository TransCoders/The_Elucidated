package gr.edu.serres.TrancCoder_TheElucitated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GamerX on 19/11/2016.
 */

class DummyInventory {
    private List<DummyItem> items;
    DummyInventory(){
        items = new ArrayList<>();
    }

    public List<DummyItem> getItems() {
        return items;
    }

    public void setItems(List<DummyItem> items) {
        items = items;
    }

    void addItem(DummyItem item){
        items.add(item);
    }
    DummyItem getItemByIconId(String id) throws ItemNotFoundException {
        for(DummyItem item:items){
            if(item.getIcon().getId().equals(id)){
                return item;
            }
        }
        throw new ItemNotFoundException();
    }
    List<String> getNamesOfAllItemsInInventory(){
        List<String> names = new ArrayList<>();
        for(DummyItem item:items){
            names.add(item.getName());
        }
        return names;
    }
    DummyItem getItem(String name) throws ItemNotFoundException {
        for(DummyItem item:items){
            if(item.getName().equals(name))
                return item;
        }
        throw new ItemNotFoundException();
    }
}
