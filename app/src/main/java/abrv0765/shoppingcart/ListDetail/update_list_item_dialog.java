package abrv0765.shoppingcart.ListDetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import abrv0765.shoppingcart.HelperClasses.ListItemNodeHelper;
import abrv0765.shoppingcart.HelperClasses.Paths;
import abrv0765.shoppingcart.R;

/**
 * Created by Ayush on 01-Nov-17.
 */

public class update_list_item_dialog extends DialogFragment {
    LayoutInflater inflater;
    View v;
    String list_id,item_id;
    EditText listItemName,quantity,price;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.dialog_item_add, null);
        quantity = (EditText) v.findViewById(R.id.add_qty);
        price=(EditText)v.findViewById(R.id.add_price);
        listItemName = (EditText) v.findViewById(R.id.add_itemName);
listItemName.setVisibility(View.GONE);
        list_id = getArguments().getString("listId");
item_id=getArguments().getString("Itemid");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

    builder.setPositiveButton("Add ListItem", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            Firebase firebaseref = new Firebase(Paths.Project_path + "/listItems/" + list_id + "/" + item_id);

    /* Make a map for the removal */
            HashMap<String, Object> updatedRemoveItemMap = new HashMap<String, Object>();

        /* Remove the item   passing null */

            if(!TextUtils.isDigitsOnly(price.getText().toString())||TextUtils.isEmpty(price.getText().toString()))
            {
                price.setError("Enter a Valid Price");
                price.setText(null);
                price.requestFocus();

            }
            else if(!TextUtils.isDigitsOnly(quantity.getText().toString())||TextUtils.isEmpty(quantity.getText().toString()))
            {
                quantity.setError("Enter Valid Amount");
                quantity.setText(null);
                quantity.requestFocus();
            }
            else {
                updatedRemoveItemMap.put("quantity", quantity.getText().toString());
                updatedRemoveItemMap.put("price",price.getText().toString());
                updatedRemoveItemMap.put("bought",true);
                firebaseref.updateChildren(updatedRemoveItemMap);


                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
            }



        /* Do the update */

        }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });


        builder.setTitle("Confirm buy");

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;


    }




}
