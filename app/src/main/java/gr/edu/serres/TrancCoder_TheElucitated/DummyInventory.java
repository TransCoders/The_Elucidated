package gr.edu.serres.TrancCoder_TheElucitated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by GamerX on 19/11/2016.
 */

class DummyInventory {
    private List<DummyItem> items;
    DummyInventory(){
        items = Collections.synchronizedList(new ArrayList<DummyItem>());
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
        ListIterator<DummyItem> iterator = items.listIterator();
        DummyItem item;
        while(iterator.hasNext()){
            item = iterator.next();
            if(item.isShowImage()){
                if(item.getIcon().getId().equals(id)){
                    return item;
                }
            }
        }
        /*for(DummyItem item:items){
            if(item.getIcon().getId().equals(id)){
                return item;
            }
        }*/
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
    void removeItem(String name){
        ListIterator<DummyItem> iterator = items.listIterator();
        while (iterator.hasNext()){
            if(iterator.next().getName().equals(name)){
                iterator.remove();
                return;
            }
        }
        /*for(DummyItem item:items){
            if(item.getName().equals(name)){
                items.remove(item);
                return;
            }
        }*/
    }
    public void addItems(DummyItem ...items){
        for(DummyItem item:items){
            this.items.add(item);
        }
    }
}
