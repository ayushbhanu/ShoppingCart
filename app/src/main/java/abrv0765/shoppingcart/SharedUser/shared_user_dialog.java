package abrv0765.shoppingcart.SharedUser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import static abrv0765.shoppingcart.FirebaseApplication.encoded_email;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.HashMap;

import abrv0765.shoppingcart.HelperClasses.ListNodeHelper;
import abrv0765.shoppingcart.HelperClasses.Paths;
import abrv0765.shoppingcart.R;

import static abrv0765.shoppingcart.FirebaseApplication.encoded_email;
import static android.R.attr.id;

/**
 * Created by Ayush on 26-Oct-17.
 */

public class shared_user_dialog extends DialogFragment {
    LayoutInflater inflater;
    View v;
    boolean exist;
    EditText useremail;
    private     Firebase ref;
    private  FirebaseUser fu;
    private String list_id;
     ListNodeHelper shoppingList;
    private String  mail;
    private  FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater=getActivity().getLayoutInflater();
        v=inflater.inflate(R.layout.shared_user_dialog,null);
        useremail=(EditText) v.findViewById(R.id.shared_user_email);


Bundle bun=getArguments();
        Gson g=new Gson();

        final String list=bun.getString("list_object");
  shoppingList=g.fromJson(list,ListNodeHelper.class);
       final  String id=bun.getString("list_id");




        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(v);

        builder.setPositiveButton("Add Shared User", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//if(checkEmailExistence(useremail.getText().toString())==true&&isEmailValid(useremail.getText().toString())==true&& useremail.getText().toString()!=user.getEmail()) {
    Firebase sList = new Firebase(Paths.Project_path + encoded_email(useremail.getText().toString()) + Paths.shared_list + id);
    sList.setValue(shoppingList);
    Log.v("sharLit", sList.toString());
    Toast.makeText(getActivity(), "List Shared Succesfully", Toast.LENGTH_SHORT).show();
//}

//else{
//
//    Toast.makeText(getActivity(), "Unsuccessful Try Again!!", Toast.LENGTH_SHORT).show();
//
//
//
//}
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        builder.setTitle("Add Shared  User");

        AlertDialog dialog=builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }





    private boolean checkEmailExistence(String mail) {
        ref=new Firebase(Paths.Project_path+"Users/"+encoded_email(mail));
          exist=false;
        /**
         * See if there is already a user (for example, if they already logged in with an associated
         * Google account.
         */
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* If there is no user, make one */
                exist = dataSnapshot.getValue() != null;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
             //   Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
            }
        });
return  exist;
    }


       private boolean isEmailValid(String email) {
              boolean isGoodEmail =
                      (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
              if (!isGoodEmail) {
                  useremail.setError("Invalid E_mail");
                  return false;
              }
              return isGoodEmail;
          }











}

