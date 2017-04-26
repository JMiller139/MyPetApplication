package com.example.jona1.mypet;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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


public class EditPetProfile extends AppCompatActivity implements View.OnClickListener{


    EditText profilePName,profilePBreed, profilePMarkings, profilePrabies_tag_num;

    private static final String TAG_RESULT = "result";
    private static final String TAG_NAME = "name";
    private static final String TAG_BREED = "breed";
    private static final String TAG_MARKINGS = "markings";
    private static final String TAG_rabies_tag_num = "rabies_tag_num";
    private final String TAG= "test";
    String appBarNameDis;
    RequestQueue requestQueue;
    String userID="1";

    // update new pet info

    private EditText editTextRNumber;
    private EditText editTextname;
    private EditText editTextbreed;
    private EditText editTextmarkings;
    private Switch editBiteSwitch;
    private Switch editTextLostSwitch;
    private EditText editTextNotes;

    private Button buttonRegister;

    private static final String REGISTER_URL = "https://php.radford.edu/~team04/userRegistration/updatePet.php?user_id=1";

    // update new pet info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_profile);
        getUserInfo();

        editTextRNumber = (EditText) findViewById(R.id.changeRNumber);
        editTextname = (EditText) findViewById(R.id.changePetName);
        editTextbreed = (EditText) findViewById(R.id.changePetBreed);
        editTextmarkings = (EditText) findViewById(R.id.changePetMarkings);
        editBiteSwitch = (Switch) findViewById(R.id.changeBiteStatus);
        editTextLostSwitch = (Switch) findViewById(R.id.lostSwitch);
        editTextNotes = (EditText) findViewById(R.id.changeNotes);

        buttonRegister = (Button) findViewById(R.id.savePetChanges);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }


    private void registerUser() {


        String rabies_tag_num = editTextRNumber.getText().toString().trim().toLowerCase();
        String name = editTextname.getText().toString().trim().toLowerCase();
        String breed = editTextbreed.getText().toString().trim().toLowerCase();
        String markings = editTextmarkings.getText().toString().trim().toLowerCase();
        String missing_status = editTextLostSwitch.getText().toString().trim().toLowerCase();
        String bite_status = editBiteSwitch.getText().toString().trim().toLowerCase();
        String notes = editTextNotes.getText().toString().trim().toLowerCase();


        register(name, breed, markings, rabies_tag_num, missing_status, bite_status, notes);
    }

    private void register(String rabies_tag_num, String name, String breed, String markings,
                          String missing_status,String bite_status,String notes) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditPetProfile.this, "Please Wait",null, true, true);
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
                data.put("name",params[0]);
                data.put("breed",params[1]);
                data.put("markings",params[2]);
                data.put("rabies_tag_num",params[3]);
                data.put("missing_status",params[4]);
                data.put("bite_status",params[5]);
                data.put("notes",params[6]);

                return  ruc.sendPostRequest(REGISTER_URL,data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,breed,markings,rabies_tag_num, missing_status, bite_status,notes);
    }


    public void getUserInfo() {

        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("https://php.radford.edu/~team04/userRegistration/getPetInfo.php?user_id="+userID,
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
        String name ;
        String breed ;
        String rabies_tag_num ;
        String markings ;
        appBarNameDis = "";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(TAG_RESULT);
            JSONObject userData = result.getJSONObject(0);
            name = (userData.getString(TAG_NAME));
            breed = userData.getString(TAG_BREED);
            rabies_tag_num = userData.getString(TAG_rabies_tag_num);
            markings = userData.getString(TAG_MARKINGS);
            appBarNameDis += name+" "+breed;
            Log.i(TAG,name);
            this.profilePName = (EditText) findViewById(R.id.changePetName);
            this.profilePBreed = (EditText) findViewById(R.id.changePetBreed);
            this.profilePrabies_tag_num = (EditText) findViewById(R.id.changeRNumber);
            this.profilePMarkings = (EditText) findViewById(R.id.changePetMarkings);

            profilePName.setText(name);
            profilePBreed.setText(breed);
            profilePrabies_tag_num.setText(rabies_tag_num);
            profilePMarkings.setText(markings);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


}