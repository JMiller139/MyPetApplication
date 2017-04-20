package com.example.jona1.mypet;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    private static final String LOGIN_URL = "https://php.radford.edu/~team04/userRegistration/login.php";
    private static final String GET_USER_INFO_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id=1";

    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button buttonLogin;

    private final String TAG = "test";

    private String fName = "";
    private String lName = "";
    private String address = "";
    private String username = "";
    private String email = "";
    private String photo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.emailEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordEditText);

        buttonLogin = (Button) findViewById(R.id.signinButton);

        buttonLogin.setOnClickListener(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_USER_INFO_URL,
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


    private void login(){
        String username = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        userLogin(username,password);
    }

    private void userLogin(final String username, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String>{
            private ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.matches(".*\\d+.*")){
                    //changed intent from content_main_screen
                    Intent intent = new Intent(LoginActivity.this,MainScreenActivity.class);
                    intent.putExtra(USER_NAME,username);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("username",params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(username,password);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            login();
        }
    }

    public void registerClicked(View v){
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        startActivity(intent);
    }


}
