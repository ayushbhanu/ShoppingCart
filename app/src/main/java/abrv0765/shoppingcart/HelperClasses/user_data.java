package abrv0765.shoppingcart.HelperClasses;

/**
 * Created by Ayush on 20-Oct-17.
 */

public class user_data {

private String email,name;

    public user_data(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public user_data() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}


