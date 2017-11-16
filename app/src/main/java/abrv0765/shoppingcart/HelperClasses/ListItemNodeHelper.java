package abrv0765.shoppingcart.HelperClasses;

/**
 * Created by Ayush on 22-Oct-17.
 */

public class ListItemNodeHelper {

    private String listItemName,owner,price,quantity;
private boolean bought;

    public ListItemNodeHelper(String listItemName, String owner, String price, String quantity, boolean bought) {
        this.listItemName = listItemName;
        this.owner = owner;
        this.price = price;
        this.quantity = quantity;
        this.bought = bought;
    }

    public ListItemNodeHelper() {
    }

//    public ListItemNodeHelper(String listItemName, String owner) {
//        this.listItemName = listItemName;
//        this.owner = owner;
//    }

    public String getListItemName() {
        return listItemName;
    }

    public void setListItemName(String listItemName) {
        this.listItemName = listItemName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
