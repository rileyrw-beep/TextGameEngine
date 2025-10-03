import java.util.*;
public class Inventory {
    private ArrayList<Item> inventoryList;



    public Inventory() {
        inventoryList = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        inventoryList.add(item);
    }

    public void removeItem(Item item) {
        inventoryList.remove(item);
    }

    public ArrayList<Item> getItems() {
        return inventoryList;
    }

    public void printInventory() {
        int counter = 0;
        for (Item item : inventoryList) {
            if (inventoryList.indexOf(item) != inventoryList.size() - 1) {
                System.out.print(item.getItemName() + ", ");
                if (counter % 3 == 0 && counter!=0) {
                    System.out.println();
                }
            }
            else {
                System.out.println(item.getItemName() + ".");
            }
            counter++;
        }
    }

    public boolean containsItem(Item item, String itemName) {
        if (item!=null) return inventoryList.contains(item);
        for (int i = 0; i < inventoryList.size(); i++) {if (inventoryList.get(i).getItemName().equals(itemName)) return true;}
        return false;
    }






}