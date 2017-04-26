package com.example.jona1.mypet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FoundPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USER_ID="USER_ID";

    private static final String GET_USER_INFO_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id=";
    private static final String JSON_URL = "";

    private String fName;
    private String lName;
    private String address;
    private String username;
    private String email;
    private String photo;
    private String fullName;
    private String userID;

    private final String TAG = "test";

    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<DataPet> lostList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        fullName = "";
        userID = "";
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            userID = (String) b.get("USER_ID");
        }
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

                            fullName = (fName+" "+lName);
                            View headerView = navigationView.getHeaderView(0);
                            TextView navUsername;
                            navUsername = (TextView) headerView.findViewById(R.id.foundPageName);
                            navUsername.setText(fullName);
                            TextView navEmail;
                            navEmail = (TextView) headerView.findViewById(R.id.foundPageEmail);
                            navEmail.setText(email);

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
        getMenuInflater().inflate(R.menu.found_page, menu);
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

    public void SendRequest(){
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        JSONParser jsonParser = new JSONParser(response);
                        jsonParser.parseJSON();
                        lostList = jsonParser.getLostPets();
                        mAdapter = new AdapterPet(lostList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FoundPage.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void toLostPage(View v){
        Intent intent = new Intent(this, MainScreenActivity.class);
        intent.putExtra(USER_ID,userID);
        startActivity(intent);
    }

}
