package com.example.jona1.mypet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.jona1.mypet.R.id.changeAddress;
import static com.example.jona1.mypet.R.id.changeEmail;
import static com.example.jona1.mypet.R.id.changeFName;
import static com.example.jona1.mypet.R.id.changeLName;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private String fname;
    private String lname;
    private String address;
    private String email;
    private String userId;


    private final String TAG = "test";

    private static final String GET_USER_INFO_URL = "https://php.radford.edu/~team04/userRegistration/getUserInfo.php?user_id=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Button buttonRegister = (Button) findViewById(R.id.saveChangesButton);
        buttonRegister.setOnClickListener(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_USER_INFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ro = new JSONObject(response);
                            JSONArray ra = ro.getJSONArray("result");
                            JSONObject userData = ra.getJSONObject(0);
                            userId = userData.getString("user_id");
                            fname = userData.getString("fname");
                            lname = userData.getString("lname");
                            address = userData.getString("address");
                            email = userData.getString("email");

                            EditText changeFname = (EditText) findViewById(changeFName);
                            changeFname.setText(fname);

                            EditText changeLname = (EditText) findViewById(changeLName);
                            changeLname.setText(lname);

                            EditText changeADDress = (EditText) findViewById(changeAddress);
                            changeADDress.setText(address);

                            EditText changeEMAIL = (EditText) findViewById(changeEmail);
                            changeEMAIL.setText(email);


                        }catch (JSONException ignored){

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
    public void onClick(View v) {

    }
}
