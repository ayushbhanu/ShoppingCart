package abrv0765.shoppingcart;

import com.firebase.client.Firebase;
import com.firebase.client.Logger;

/**
 * Includes one-time initialization of Firebase related code
 */
//store global variables that jump from Activity to Activity. Like Asynctask.

public class FirebaseApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

        // debug helps to identify error while writing or reading data
        // turns on firebase debug mode
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);



    }
    public static String encoded_email(String email){

        return email.replace('.',',');



    }
}