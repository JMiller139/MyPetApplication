package com.example.jona1.mypet;

import android.app.ProgressDialog;
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
    String userID="1";

    // register new user info

    private Switch notificationSwitch;
    private Switch locationSwitch;
    private Spinner spinner;

    private Button button4;

    private final String REGISTER_URL = "https://php.radford.edu/~team04/userRegistration/getSettings.php?user_id=" + userID;

    // end register new user info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getUserInfo();

        notificationSwitch = (Switch) findViewById(R.id.changeRNumber);
        locationSwitch = (Switch) findViewById(R.id.changePetName);
        spinner = (Spinner) findViewById(R.id.changePetBreed);

        button4 = (Button) findViewById(R.id.savePetChanges);

        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == button4){
            registerUser();
        }
    }


    private void registerUser() {


        String notifications = String.valueOf(notificationSwitch.isChecked()).trim().toLowerCase();
        String location = String.valueOf(locationSwitch.isChecked()).trim().toLowerCase();
        String radius = spinner.getSelectedItem().toString().trim().toLowerCase();


        register(notifications,location,radius);
    }

    private void register(String notifications, String location, String radius) {
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

                return  ruc.sendPostRequest(REGISTER_URL,data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(notifications,location,radius);
    }


    public void getUserInfo() {

        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("https://php.radford.edu/~team04/userRegistration/getSettings.php?user_id="+userID,
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
        String notifications ;
        String location ;
        String radius ;

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(TAG_RESULT);
            JSONObject userData = result.getJSONObject(0);
            notifications = (userData.getString(TAG_NOTIFICATIONS));
            location = userData.getString(TAG_LOCATION);
            radius = userData.getString(TAG_RADIUS);

            Log.i(TAG,notifications);
            notificationSwitch = (Switch) findViewById(R.id.changeRNumber);
            locationSwitch = (Switch) findViewById(R.id.changePetName);
            spinner = (Spinner) findViewById(R.id.changePetBreed);

            if (notifications.equals("true")) {notificationSwitch.setChecked(true);}
            else {notificationSwitch.setChecked(true);}

            if (location.equals("true")) {locationSwitch.setChecked(true);}
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
