package abrv0765.shoppingcart.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import abrv0765.shoppingcart.FirebaseApplication;
import abrv0765.shoppingcart.HelperClasses.Paths;
import abrv0765.shoppingcart.HelperClasses.user_data;
import abrv0765.shoppingcart.MainActivity;
import abrv0765.shoppingcart.R;

import static abrv0765.shoppingcart.FirebaseApplication.encoded_email;

/**
 * Created by Ayush on 07-Oct-17.
 */
public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    EditText email,reg_username;
    EditText password;
    private ProgressDialog pd;
    String us;
    FirebaseApplication fireApp=((FirebaseApplication) this.getApplication());
    private  FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_pwd);
        reg_username=(EditText)findViewById(R.id.reg_user);
        pd = new ProgressDialog(this);
        pd = new ProgressDialog(this);
        pd.setTitle(getString(R.string.progress_dialog_loading));
        pd.setMessage("Authenticating");
        pd.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        Button btnSingUp=(Button)findViewById(R.id.registerbutton);
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passSign();

            }
        });

    }

  public void   passSign(){
final String mail=email.getText().toString();
      String pass=password.getText().toString();
       us=reg_username.getText().toString();
     // fireApp.setUser(userdata);
if(isEmailValid(mail)==true&&isPasswordValid(pass)==true&&isUserNameValid(us)==true) {
    pd.show();
    mAuth.createUserWithEmailAndPassword(mail, pass)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user_data's information
                        Log.d(TAG, "createUserWithEmail:success");
                         user = mAuth.getCurrentUser();
                     // update();
                        user_data ud=new user_data( user.getEmail(),us);
                        //fireApp.setUser(ud);
                     //   Log.v("hello2",fireApp.getUser().getEmail()+" "+fireApp.getUser().getName());
                        Log.v("hello", user.getEmail()+" "+us);

                        Firebase ref=new Firebase(Paths.Project_path+Paths.User_node);
                        ref.child(encoded_email(user.getEmail())).setValue(ud);

                        Toast.makeText(SignUpActivity.this, "Succesful", Toast.LENGTH_SHORT).show();
                        pd.hide();

                        updateUI();
                        finish();

                    } else {
                        // If sign in fails, display a message to the user_data.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, task.getException().toString(),
                                Toast.LENGTH_SHORT).show();
                    }


                }
            });
}


}




    public void updateUI(){
Log.v("hello",user.getDisplayName()+" "+user.getEmail());
        Intent i=new Intent(SignUpActivity.this,MainActivity.class);
        i.putExtra("name",us);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);

    }




    private boolean isEmailValid(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            this.email.setError("Invalid E_mail");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isUserNameValid(String userName) {
        if (userName.equals("")) {
            reg_username.setError("Give correct Name");
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
           this. password.setError("Invalid Password");
            return false;
        }
        return true;
    }


    public void update(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });



    }






}



