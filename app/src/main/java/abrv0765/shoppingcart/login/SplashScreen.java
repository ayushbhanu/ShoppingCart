package abrv0765.shoppingcart.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import abrv0765.shoppingcart.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i;
                i=new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
