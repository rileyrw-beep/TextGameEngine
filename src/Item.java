public class Item {
    private String itemName;
    private String itemDescription;
    private boolean isCollectible;


    public Item (String name, String description) {
        this.itemName = name;
        this.itemDescription = description;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
