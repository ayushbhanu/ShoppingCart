package abrv0765.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static abrv0765.shoppingcart.FirebaseApplication.encoded_email;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import  abrv0765.shoppingcart.AddList.add_list;
import abrv0765.shoppingcart.HelperClasses.ListNodeHelper;
import abrv0765.shoppingcart.HelperClasses.Paths;
import abrv0765.shoppingcart.HelperClasses.user_data;
import abrv0765.shoppingcart.ListDetail.ListItem;
import abrv0765.shoppingcart.SharedUser.shared_user_list;
import abrv0765.shoppingcart.login.LoginActivity;
import  abrv0765.shoppingcart.AddList.add_list_Adapter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   private String username;
    private TextView tv;
    private EditText add_list;
    private add_list_Adapter adap;
    private ListView mListView;
    private FirebaseUser us;
    String name;

    //  private user_data user=user_data.returnInastance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         name=getIntent().getStringExtra("name");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.add_list_listView);
        setSupportActionBar(toolbar);
add_list=(EditText)findViewById(R.id.add_listname);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            add_list al=new add_list();

                Bundle args = new Bundle();
                args.putString("name", name);
                al.setArguments(args);




                al.show(getSupportFragmentManager(),"AddList");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // username = getIntent().getStringExtra("UserCurrent");
       tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.currentUser);
us=FirebaseAuth.getInstance().getCurrentUser();

        if(us.getDisplayName()==null){
            tv.setText(name);
        }
        else{
             tv.setText(us.getDisplayName());
       }
        // Log.v("activity123",username);
        Firebase ref=new Firebase(Paths.Project_path+encoded_email(us.getEmail()+"/Lists"));

        adap=new add_list_Adapter(MainActivity.this,ListNodeHelper.class,R.layout.layout_list_home_view,ref,encoded_email(us.getEmail()));
        mListView.setAdapter(adap);

        /**
         * Set interactive bits, such as click events and adapters
         */
        Log.v("hello","done" + "");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(view.getContext(),ListItem.class);
             //   Toast.makeText(MainActivity.this, "gREAT dONE", Toast.LENGTH_SHORT).show();
                String listId = adap.getRef(position).getKey();

                i.putExtra("list_id",listId);
                startActivity(i);
            }
        });





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
if(id==R.id.shared_list){
    Intent i=new Intent(MainActivity.this,shared_user_list.class);
    startActivity(i);


}
      else  if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }  else if (id == R.id.sign_out) {

            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
//user=null;
    finish();
            Toast.makeText(this, "SignOut Succesful", Toast.LENGTH_SHORT).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






}
