package com.example.jona1.mypet;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{


    EditText profileFName,profileLName, profileAddress, profileEmail;

    private static final String TAG_RESULT = "result";
    private static final String TAG_FNAME = "fname";
    private static final String TAG_LNAME = "lname";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_EMAIL = "email";
    private final String TAG= "test";
    String appBarNameDis;
    RequestQueue requestQueue;
    String userID="1";

    // register new user info

    private EditText editTextEmail;
    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextAddress;

    private Button buttonRegister;

    private static final String REGISTER_URL = "https://php.radford.edu/~team04/userRegistration/hey.php";

    // end register new user info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getUserInfo();

        editTextEmail = (EditText) findViewById(R.id.changeEmail);
        editTextFname = (EditText) findViewById(R.id.changeFName);
        editTextLname = (EditText) findViewById(R.id.changeLName);
        editTextAddress = (EditText) findViewById(R.id.changeAddress);

        buttonRegister = (Button) findViewById(R.id.saveChangesButton);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }


    private void registerUser() {


        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String fname = editTextFname.getText().toString().trim().toLowerCase();
        String lname = editTextLname.getText().toString().trim().toLowerCase();
        String address = editTextAddress.getText().toString().trim().toLowerCase();


        register(email,fname,lname,address);
    }

    private void register(String email, String fname, String lname, String address) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditProfileActivity.this, "Please Wait",null, true, true);
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
                data.put("fname",params[0]);
                data.put("lname",params[1]);
                data.put("address",params[2]);
                data.put("email",params[3]);

                return  ruc.sendPostRequest(REGISTER_URL,data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(fname,lname,address,email);
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
        String fName ;
        String lName ;
        String email ;
        String address ;
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


}