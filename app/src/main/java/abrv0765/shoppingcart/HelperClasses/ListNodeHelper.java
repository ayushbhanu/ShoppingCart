package abrv0765.shoppingcart.HelperClasses;

/**
 * Created by Ayush on 20-Oct-17.
 */

public class ListNodeHelper {

    private String creator, listName,userName;
    private int totalUsers;

    public ListNodeHelper() {
    }

    public ListNodeHelper(String creator, String listName, String userName,int totalUsers) {
        this.creator = creator;
        this.listName = listName;
        this.userName = userName;
        this.totalUsers=totalUsers;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
}