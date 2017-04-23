package com.example.jona1.mypet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView tv;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String JSON_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php";
    EditText appBarName,appBarEmail, profileFName,profileLName, profileAddress, profileEmail;
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
    private static final String USER_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id=";
    RequestQueue requestQueue;
    String userID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userID = "";
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            userID = (String) b.get("USER_ID");
        }
        getUserInfo();
    }
    public void getUserInfo() {

        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(USER_URL+userID,
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
            this.profileFName = (EditText) findViewById(R.id.changeFName);
            this.profileLName = (EditText) findViewById(R.id.changeLName);
            this.profileEmail = (EditText) findViewById(R.id.changeEmail);
            this.profileAddress = (EditText) findViewById(R.id.changeAddress);

            profileFName.setText(fName);
            profileLName.setText(lName);
            profileEmail.setText(email);
            profileAddress.setText(address);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }
}