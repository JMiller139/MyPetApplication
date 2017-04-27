package com.example.jona1.mypet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "test";
    private static final String GET_USER_INFO_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id=";
    private static final String GET_USER_PETS = "https://php.radford.edu/~team04/userRegistration/getPetInfo.php?user_id=";
    public static final String USER_ID="USER_ID";

    private String fName = "";
    private String lName = "";
    private String address = "";
    private String username = "";
    private String email = "";
    private String photo = "";
    private String userID;
    private String petID;
    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataPet> petList = new ArrayList<DataPet>();
    private PetListAdapter adapter;
    private ListView listView;
    private ProgressDialog pDialog;
    private Context context;
    private String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        userID = "";
        petID="";
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            userID = (String) b.get("USER_ID");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_USER_INFO_URL+userID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ro = new JSONObject(response);
                            JSONArray ra = ro.getJSONArray("result");
                            JSONObject userData = ra.getJSONObject(0);
                            fName = userData.getString("fname");
                            lName = userData.getString("lname");
                            address = userData.getString("address");
                            username = userData.getString("username");
                            email = userData.getString("email");
                            //photo = userData.getString("photo");

                            View headerView = navigationView.getHeaderView(0);
                            TextView navUsername;
                            fullName = (fName+" "+lName);
                            navUsername = (TextView) headerView.findViewById(R.id.profileNavUsrName);
                            navUsername.setText(fName+" "+lName);
                            navUsername.setText(fullName);
                            TextView navEmail;
                            navEmail = (TextView) headerView.findViewById(R.id.profileNavEmail);
                            navEmail.setText(email);

                            TextView firstName = (TextView) findViewById(R.id.firstName);
                            firstName.setText(fName);
                            TextView lastName = (TextView) findViewById(R.id.lastName);
                            lastName.setText(lName);
                            TextView userName = (TextView) findViewById(R.id.userName);
                            userName.setText(username);
                            TextView addressTV = (TextView) findViewById(R.id.address);
                            addressTV.setText(address);
                            TextView emailTV = (TextView) findViewById(R.id.email);
                            emailTV.setText(email);

                        }catch (JSONException e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG,"error");
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        context = getApplicationContext();
        listView = (ListView) findViewById(R.id.petList);
        adapter = new PetListAdapter(UserProfileActivity.this,petList);
        listView.setAdapter(adapter);
        getPetInfo();

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
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOut) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainScreenActivity.class);
            intent.putExtra(USER_ID,userID);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra(USER_ID,userID);
            startActivity(intent);
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(USER_ID,userID);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            intent.putExtra(USER_ID,userID);
            startActivity(intent);
        }
        else if (id == R.id.nav_inbox) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addPet(View v){
        Intent intent = new Intent(UserProfileActivity.this, CreatePetProfileActivity.class);
        intent.putExtra(USER_ID,userID);
        startActivity(intent);
    }

    public void addEditUser(View v){
        Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
        intent.putExtra(USER_ID,userID);
        startActivity(intent);
    }

    public void editPet(View v){
        Intent intent = new Intent(UserProfileActivity.this, EditPetProfile.class);
        intent.putExtra(USER_ID,userID);
        startActivity(intent);
    }
    public void getPetInfo(){
        pDialog = new ProgressDialog(UserProfileActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,GET_USER_PETS+userID,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        hidePDialog();
//                        JSONParser jsonParser = new JSONParser(response);
//                        jsonParser.parseJSON();
//                        petList = jsonParser.getPets();
//                        adapter.notifyDataSetChanged();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d(TAG,"Error: " + error.getMessage());
//                        Toast.makeText(UserProfileActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
//                        hidePDialog();
//                    }
//               });
//        AppController.getInstance().addToRequestQueue(stringRequest);
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest petListReq = new JsonObjectRequest(Request.Method.GET,GET_USER_PETS+userID,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,response.toString());
                        hidePDialog();
                        try {
                            JSONArray ra = response.getJSONArray("result");
                                 for(int i=0; i<ra.length(); i++) {
                                     JSONObject obj = ra.getJSONObject(i);
                                     DataPet currentPet = new DataPet();
                                     currentPet.SetPetName(obj.getString("name"));
                                     currentPet.SetBreed(obj.getString("breed"));
                                     currentPet.SetSpecies(obj.getString("species"));
                                     petList.add(currentPet);
                                 }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        VolleyLog.d(TAG,"Error: it didn't work");
                        hidePDialog();
                    }

                });
        AppController.getInstance().addToRequestQueue(petListReq);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
}

