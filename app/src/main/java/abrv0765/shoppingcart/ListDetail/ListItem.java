package abrv0765.shoppingcart.ListDetail;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;


import java.util.ArrayList;

import abrv0765.shoppingcart.HelperClasses.ListItemNodeHelper;
import abrv0765.shoppingcart.HelperClasses.ListNodeHelper;
import abrv0765.shoppingcart.HelperClasses.Paths;
import abrv0765.shoppingcart.PdfGeneration;
import abrv0765.shoppingcart.R;
import abrv0765.shoppingcart.SharedUser.shared_user_dialog;

import static abrv0765.shoppingcart.R.id.action_share;
import static abrv0765.shoppingcart.FirebaseApplication.encoded_email;


public class ListItem extends AppCompatActivity {
 private   String listId;
    private Gson g;
    private ListView mListView;
    private add_list_item_adapter adap;
    private ValueEventListener valueEventListener;
    private FirebaseUser user;
   private  Firebase ref,uref,listItemref;
    String Item_id;
    public     ListNodeHelper shoppingList;
    public ListItemNodeHelper itemNode;
    private ArrayList<String> item_name=new ArrayList<>();
    private ArrayList<Integer> item_price=new ArrayList<>();
    private ArrayList<Integer> item_quantity=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        mListView=(ListView)findViewById(R.id.list_Item_listView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_detail_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_ListItem_dialog al=new add_ListItem_dialog();
                Bundle args = new Bundle();
                args.putString("listId", listId);
                al.setArguments(args);
                al.show(getFragmentManager(),"AddList");
            }
        });
         listId = getIntent().getStringExtra("list_id");
//setTitle(listId);
user= FirebaseAuth.getInstance().getCurrentUser();
uref=new Firebase(Paths.Project_path + encoded_email(user.getEmail().toString())+"/Lists/"+listId);

        ref=new Firebase(Paths.Project_path+"listItems/"+listId);
        valueEventListener=  uref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                /**
                 * Saving the most recent version of current shopping list into mShoppingList if present
                 * finish() the activity if the list is null (list was removed or unshared by it's owner
                 * while current user is in the list details activity)
                 */
                 shoppingList = snapshot.getValue(ListNodeHelper.class);

                if (shoppingList == null) {
                    finish();
                    /**
                     * Make sure to call return, otherwise the rest of the method will execute,
                     * even after calling finish.
                     */
                    return;
                }
                //  mShoppingList = shoppingList;

                /* Calling invalidateOptionsMenu causes onCreateOptionsMenu to be called */
                //  invalidateOptionsMenu();

                /* Set title appropriately. */
                setTitle(shoppingList.getListName());
                Log.v("heklo"," "+snapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(ListItem.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        adap=new add_list_item_adapter(ListItem.this,ListItemNodeHelper.class,R.layout.list_item_layout,ref,listId);
        mListView.setAdapter(adap);



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Item_id =adap.getRef(position).getKey();
update_list_item_dialog ud=new update_list_item_dialog();
                Bundle b=new Bundle();
                b.putString("listId",listId);
                b.putString("Itemid",Item_id);

                ud.setArguments(b);
                ud.show(getFragmentManager(),"AddUser");
            }
        });


        Log.v("totatitems"," "+mListView.getAdapter().getCount()+" ");



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_item_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_list) {
deleteList();
        }
else if(id==R.id.action_pdf){

generatePdf();


        }
        else if(id==action_share){
            share();

        }

        return super.onOptionsItemSelected(item);
    }


    public void share(){




         g=new Gson();


        shared_user_dialog al=new shared_user_dialog();


Bundle bun=new Bundle();
        bun.putString("list_id",listId);
        bun.putString("list_object",g.toJson(shoppingList));

        al.setArguments(bun);



        al.show(getFragmentManager(),"AddUser");




    }

    public void deleteList(){


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListItem.this).
                setTitle(getString(R.string.remove_item_list))
                .setMessage(getString(R.string.dialog_message_are_you_sure_remove_item))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ref.removeValue();
                        uref.removeValue();
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                                /* Dismiss the dialog */
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }




public void generatePdf(){
for(int i=0;i<mListView.getCount();i++) {
    listItemref = new Firebase(Paths.Project_path + "listItems/" +listId+"/"+ adap.getRef(i).getKey());

listItemref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        itemNode=dataSnapshot.getValue(ListItemNodeHelper.class);
item_price.add(Integer.parseInt(itemNode.getPrice()));item_name.add(itemNode.getListItemName());item_quantity.add(Integer.parseInt(itemNode.getQuantity()));
        Log.v("hahaah", itemNode.getListItemName()+""+ itemNode.getPrice());

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
});

}

    Log.v("hee1",item_name.size()+""+item_price.size());

    g=new Gson();

    Intent i=new Intent(this, PdfGeneration.class);
    Bundle bundle=new Bundle();
//    bundle.putString("item_name",g.toJson(item_name));
//    bundle.putString("item_price",g.toJson(item_price));
    i.putExtra("item_name",item_name);
    i.putExtra("item_price",item_price);
    i.putExtra("item_quantity",item_quantity);


    startActivity(i);




}

}
