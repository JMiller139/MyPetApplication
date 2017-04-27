package com.example.jona1.mypet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_RESULT = "result";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_RADIUS = "radius";
    private final String TAG= "test";
    RequestQueue requestQueue;
    String userID;

    // register new user info

    private Switch notificationSwitch;
    private Switch locationSwitch;
    private Spinner spinner;

    private final String GET_SETTINGS_URL = "https://php.radford.edu/~team04/userRegistration/getSettings.php?user_id=" + userID;
    private final String SET_SETTINGS_URL = "https://php.radford.edu/~team04/userRegistration/updateSettings.php?user_id=" + userID;
    // end register new user info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            userID = (String) b.get("USER_ID");
            Log.i(TAG,userID);
        }

        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        locationSwitch = (Switch) findViewById(R.id.locationSwitch);
        spinner = (Spinner) findViewById(R.id.spinner);

        getUserInfo();


    }

    @Override
    public void onClick(View v) {
            registerUser();
    }


    private void registerUser() {


        String notifications = String.valueOf(notificationSwitch.isChecked()).trim().toLowerCase();
        String location = String.valueOf(locationSwitch.isChecked()).trim().toLowerCase();
        String radius = spinner.getSelectedItem().toString().trim().toLowerCase();


        register(notifications,location,radius,userID);
    }

    private void register(String notifications, String location, String radius, String userID) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SettingActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("notifications",params[0]);
                data.put("location",params[1]);
                data.put("radius",params[2]);
                data.put("user_id",params[3]);

                return  ruc.sendPostRequest(SET_SETTINGS_URL,data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(notifications,location,radius,userID);
    }


    public void getUserInfo() {

        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(GET_SETTINGS_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG,response);
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
        String notifications ;
        String location ;
        String radius ;
        Log.i(TAG,response);

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(TAG_RESULT);
            JSONObject userData = result.getJSONObject(0);
            notifications = userData.getString("push_notifications");
            location = userData.getString("location_given");
            radius = userData.getString(TAG_RADIUS);

            Log.i(TAG,notifications);

            if (notifications.equals("1")) {notificationSwitch.setChecked(true);}
            else {notificationSwitch.setChecked(false);}

            if (location.equals("1")) {locationSwitch.setChecked(true);}
            else {locationSwitch.setChecked(false);}

            if (radius.equals("1")) {spinner.setSelection(0);}
            else if (radius.equals("5")) {spinner.setSelection(1);}
            else if (radius.equals("10")) {spinner.setSelection(2);}
            else if (radius.equals("20")) {spinner.setSelection(3);}
            else if (radius.equals("50")) {spinner.setSelection(4);}
            else if (radius.equals("150")) {spinner.setSelection(5);}


        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
