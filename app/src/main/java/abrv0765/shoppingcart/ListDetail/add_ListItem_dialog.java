package abrv0765.shoppingcart.ListDetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import abrv0765.shoppingcart.HelperClasses.ListItemNodeHelper;
import abrv0765.shoppingcart.HelperClasses.ListNodeHelper;
import abrv0765.shoppingcart.HelperClasses.Paths;
import abrv0765.shoppingcart.R;

import static java.lang.Boolean.TRUE;

/**
 * Created by Ayush on 22-Oct-17.
 */

public class add_ListItem_dialog extends DialogFragment{
    LayoutInflater inflater;
    View v;
    String list_id;
    EditText listItemName,quantity,price;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater=getActivity().getLayoutInflater();
        v=inflater.inflate(R.layout.dialog_item_add,null);
quantity=(EditText) v.findViewById(R.id.add_qty);
        listItemName=(EditText) v.findViewById(R.id.add_itemName);
price=(EditText)v.findViewById(R.id.add_price);
        price.setVisibility(View.GONE);
list_id=getArguments().getString("listId");

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(v);

        builder.setPositiveButton("Add ListItem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseUser fu= FirebaseAuth.getInstance().getCurrentUser() ;
                Firebase ref=new Firebase(Paths.Project_path+"listItems/"+list_id);

                ListItemNodeHelper lh=new ListItemNodeHelper(listItemName.getText().toString(),fu.getDisplayName(),"10",quantity.getText().toString(),false);
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
