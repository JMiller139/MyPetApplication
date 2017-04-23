package com.example.jona1.mypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditPetProfile extends AppCompatActivity {

    private final String TAG = "test";

    private static final String GET_PET_INFO_URL = "https://php.radford.edu/~team04/userRegistration/getPetInfo.php?user_id=1";

    private String petId;
    private String petName;
    private String species;
    private String breed;
    private String markings;
    private String missingStatus;
    private String rabiesTagNum;
    private String biteStatus;
    private String notes;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_profile);

        userID = "";
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            userID = (String) b.get("USER_ID");
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_PET_INFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ro = new JSONObject(response);
                            JSONArray ra = ro.getJSONArray("result");
                            JSONObject petData = ra.getJSONObject(0);
                            petId = petData.getString("pet_id");
                            petName = petData.getString("name");
                            species = petData.getString("species");
                            breed = petData.getString("breed");
                            markings = petData.getString("markings");
                            missingStatus = petData.getString("missing_status");
                            rabiesTagNum = petData.getString("rabies_tag_num");
                            biteStatus = petData.getString("bite_status");
                            notes = petData.getString("notes");

                            EditText changePetName = (EditText) findViewById(R.id.changePetName);
                            changePetName.setText(petName);
                            EditText changePetBreed = (EditText) findViewById(R.id.changePetBreed);
                            changePetBreed.setText(breed);
                            EditText changePetMarkings = (EditText) findViewById(R.id.changePetMarkings);
                            changePetMarkings.setText(markings);
                            EditText changeRNumber = (EditText) findViewById(R.id.changeRNumber);
                            changeRNumber.setText(rabiesTagNum);
                            Switch changeBiteStatus = (Switch) findViewById(R.id.changeBiteStatus);
                            if(biteStatus == "1");{
                                changeBiteStatus.setChecked(true);
                            }
                            EditText changeNotes = (EditText) findViewById(R.id.changeNotes);
                            changeNotes.setText(notes);
                            Switch lostSwitch = (Switch) findViewById(R.id.lostSwitch);
                            if(missingStatus == "1"){
                                lostSwitch.setChecked(true);
                            }

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
}
