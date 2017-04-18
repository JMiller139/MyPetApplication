package com.example.jona1.mypet;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String USER_NAME = "USER_NAME";

    public static final String PASSWORD = "PASSWORD";

    private static final String LOGIN_URL = "https://php.radford.edu/~team04/userRegistration/login.php";
    private EditText editTextUserName;
    private EditText editTextPassword;

    private Button buttonLogin;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private static final String USER_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id=1";

    private TextView appBarFName;
    private TextView appBarEmail;
    String jsonURL = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Object.JSON";
    TextView results;
    RequestQueue requestQueue;
    private Bundle info = new Bundle();

    private static final String TAG_RESULT = "result";
    private static final String TAG_FNAME = "fname";
    private static final String TAG_LNAME = "lname";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String USER_DATA = "USER_DATA";
    JSONArray userDataArray = null;
    private final String TAG= "test";
    String username;
    String password;
    private static final String INFO = "INFO";
    private UserData data = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.emailEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordEditText);

        buttonLogin = (Button) findViewById(R.id.signinButton);

        buttonLogin.setOnClickListener(this);
    }


    private void login() {
        username = editTextUserName.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        userLogin(username, password);
    }

    private void userLogin(final String username, final String password) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equalsIgnoreCase("success")) {
                    //changed intent from content_main_screen
                    Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                    intent.putExtra(USER_NAME, username);
                    intent.putExtra(INFO,info);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("username", params[0]);
                data.put("password", params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL, data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(username, password);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            login();
//            getUserInfo();
        }
    }


    public void registerClicked(View v) {
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        startActivity(intent);
    }
//
//    public void getUserInfo() {
//        requestQueue = Volley.newRequestQueue(this);
//            StringRequest stringRequest = new StringRequest(USER_URL,
//                        new Response.Listener<String>() {
//
//                            @Override
//                            public void onResponse(String response) {
//                                showJSON(response);
//                            }
//                },
//                        new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i(TAG,error.toString());
//                    }
//                });
//        requestQueue.add(stringRequest);
//    }
//    public void showJSON(String response){
//        String fName = "";
//        String lName = "";
//        String vc = "";
//        try{
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray(TAG_RESULT);
//            JSONObject userData = result.getJSONObject(0);
//            fName = (userData.getString(TAG_FNAME));
//            lName = userData.getString(TAG_LNAME);
//            Log.i(TAG,fName);
//            info.putString("fName",userData.getString(TAG_FNAME));
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//    }

//    public void getData() {
//        UserData userData = new UserData();
//        JSONObject jsonObject = userData.getJSONFromUrl();
//
//        try{
//            userDataArray = jsonObject.getJSONArray(TAG_RESULT);
//            JSONObject data = userDataArray.getJSONObject(0);
//
//            String id = data.getString(TAG_EMAIL);
////            appBarFName = (TextView) findViewById(R.id.tester);
////            appBarFName.setText(id);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//
//    }
}
