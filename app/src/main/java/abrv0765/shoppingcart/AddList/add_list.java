package abrv0765.shoppingcart.AddList;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.app.AlertDialog;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import abrv0765.shoppingcart.HelperClasses.ListNodeHelper;
import abrv0765.shoppingcart.HelperClasses.Paths;
import abrv0765.shoppingcart.R;
import static abrv0765.shoppingcart.FirebaseApplication.encoded_email;

/**
 * Created by Ayush on 21-Oct-17.
 */

public class add_list extends DialogFragment {
LayoutInflater inflater;
    View v;
    EditText listName;
    String name;
    ListNodeHelper lh;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        name=getArguments().getString("name");

inflater=getActivity().getLayoutInflater();
        v=inflater.inflate(R.layout.fragment_add_list,null);

        listName=(EditText) v.findViewById(R.id.add_listname);



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(v);

builder.setPositiveButton("Add List", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {

        FirebaseUser fu=FirebaseAuth.getInstance().getCurrentUser() ;
        Firebase ref=new Firebase(Paths.Project_path+encoded_email(fu.getEmail())+"/Lists");
if(fu.getDisplayName()==null){
         lh=new ListNodeHelper(fu.getEmail(),listName.getText().toString(),name,1);}
        else{
     lh=new ListNodeHelper(fu.getEmail(),listName.getText().toString(),fu.getDisplayName(),1);}


        Firebase refer=ref.push();
refer.setValue(lh);



        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
    }
}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
});



        builder.setTitle("Add List");

AlertDialog dialog=builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }



}
