package com.example.jona1.mypet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
//import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<DataPet> lostList;
    public static final String JSON_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php";
    TextView appBarName,appBarEmail, profileFName,profileLName, profileAddress, profileEmail;
    private static final String userData = "USER_DATA";
    NavigationView navigationView;
    private static final String TAG_RESULT = "result";
    private static final String TAG_FNAME = "fname";
    private static final String TAG_LNAME = "lname";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String USER_DATA = "USER_DATA";
    private final String TAG= "test";
    String appBarNameDis;
    private static final String INFO = "INFO";
    private UserData data = new UserData();
    private static final String USER_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id=1";
    RequestQueue requestQueue;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        data = (UserData) getIntent().getSerializableExtra(userData);
        this.tv = (TextView) findViewById(R.id.TVusername);


        Bundle b = intent.getExtras();
        if (b != null) {
            this.tv.setText("Welcome back " + (String) b.get("USER_NAME"));
            userID = "1";
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.listLostPet);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getUserInfo();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
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
            Intent loginScreen = new Intent(this, LoginActivity.class);
            loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginScreen);
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
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_inbox) {

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
                          Toast.makeText(MainScreenActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                      }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void getUserInfo() {

        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id="+userID,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG,error.toString());
                    }
                });
        requestQueue.add(stringRequest);
    }
    public void showJSON(String response){
        String fName = "";
        String lName = "";
        String email = "";
        String address = "";
        appBarNameDis = "";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(TAG_RESULT);
            JSONObject userData = result.getJSONObject(0);
            fName = (userData.getString(TAG_FNAME));
            lName = userData.getString(TAG_LNAME);
            email = userData.getString(TAG_EMAIL);
            address = userData.getString(TAG_ADDRESS);
            appBarNameDis += fName+" "+lName;
            Log.i(TAG,fName);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            View v = navigationView.getHeaderView(0);
            this.appBarName = (TextView) findViewById(R.id.appBarName);
            this.appBarEmail = (TextView) findViewById(R.id.appBarEmail);
//            this.profileFName = (TextView) findViewById(R.id.firstName);
//            this.profileLName = (TextView) findViewById(R.id.lastName);
//            this.profileEmail = (TextView) findViewById(R.id.email);
//            this.profileAddress = (TextView) findViewById(R.id.address);
            appBarName.setText(appBarNameDis);
            appBarEmail.setText(email);
//            profileFName.setText(fName);
//            profileLName.setText(lName);
//            profileEmail.setText(email);
//            profileAddress.setText(address);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
// public void requestData(String uri) {
//
//        StringRequest request = new StringRequest(uri,
//
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        gamesList = JSONParser.parseData(response);
//                        AdapterPet adapter = new AdapterPet(MainScreenActivity.this, gamesList);
//                        lv.setAdapter(adapter);
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(MainScreenActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(request);
//    }
//}
//    private class AsyncFetch extends AsyncTask<String, String, String>{
//
//        ProgressDialog pdLoading = new ProgressDialog(MainScreenActivity.this);
//        HttpURLConnection connection;
//        URL url = null;
//
//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//
//            pdLoading.setMessage("\tLoading...");
//            pdLoading.setCancelable(false);
//            pdLoading.show();
//
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            try{
//                url = new URL("https://php.radford.edu/~team04/userRegistration/getUserInfo.php");
//            } catch (MalformedURLException e){
//                e.printStackTrace();
//                return e.toString();
//            }
//            try{
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setReadTimeout(READ_TIMEOUT);
//                connection.setConnectTimeout(CONNECTION_TIMEOUT);
//                connection.setRequestMethod("GET");
//            }catch (IOException e1){
//                e1.printStackTrace();
//                return e1.toString();
//            }
//            try{
//                int response_code = connection.getResponseCode();
//
//                if(response_code == HttpURLConnection.HTTP_OK) {
//                    InputStream input = connection.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//
//                    return (result.toString());
//                }else{
//                    return ("Unsuccessful");
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//                return e.toString();
//            }finally {
//                connection.disconnect();
//            }
//        }
//
//
//        @Override
//        protected void onPostExecute(String result){
//
//            pdLoading.dismiss();
//            List<DataPet> data = new ArrayList<>();
//
//            pdLoading.dismiss();
//            try{
//
//                JSONArray jArray = new JSONArray("user_id");
//
//                for(int i=0; i<jArray.length();++i){
//                    try {
//                        JSONObject json_data = jArray.getJSONObject(i);
//                        DataPet petData = new DataPet();
////                        petData.petImage = json_data.getString("fish_img");
//                        petData.petName = json_data.getString("user_id");
//                        petData.catName = json_data.getString("address");
//                        petData.sizeName = json_data.getString("username");
//                        petData.price = json_data.getString("email");
//                        data.add(petData);
//                    }catch (JSONException e ){
//                        e.printStackTrace();
//                    }
//
//                }
//
////                mAdapter = new AdapterPet(MainScreenActivity.this,data);
////                mRecyclerView.setAdapter(mAdapter);
////                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainScreenActivity.this));
//
//
//            }catch (JSONException e){
//                Toast.makeText(MainScreenActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//}
