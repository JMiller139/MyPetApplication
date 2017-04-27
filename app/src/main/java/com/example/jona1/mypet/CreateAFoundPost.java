package com.example.jona1.mypet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CreateAFoundPost extends AppCompatActivity {

    private String pName;
    private String pSpecies;
    private String pBreed;
    private String pMarkings;
    private String pNotes;
    private String pPhoto;
    private String pRabies;

    private String userID;

    private static final String FOUND_URL = "https://php.radford.edu/~team04/userRegistration/found.php?user_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_afound_post);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            userID = (String) b.get("USER_ID");
        }


    }

    public void createFoundPost(View v){
        EditText petNameTextField = (EditText) findViewById(R.id.petNameTextField);
        EditText speciesEditText = (EditText) findViewById(R.id.speciesEditText);
        EditText breedEditText = (EditText) findViewById(R.id.breedEditText);
        EditText markingsEditText = (EditText) findViewById(R.id.markingsEditText);
        EditText rabiesEditText = (EditText) findViewById(R.id.rabiesEditText);
        EditText notesSection = (EditText) findViewById(R.id.notesSection);

        this.pName = petNameTextField.getText().toString();
        this.pSpecies = speciesEditText.getText().toString();
        this.pBreed = breedEditText.getText().toString();
        this.pMarkings = markingsEditText.getText().toString();
        this.pNotes = notesSection.getText().toString();
        this.pRabies = rabiesEditText.getText().toString();
        this.pPhoto = "photo";

        register(pName, pSpecies, pBreed, pPhoto, pMarkings, pRabies, pNotes, userID);
    }

    private void register(String pName, String pSpecies, String pBreed , String pPhoto, String pMarkings,
                          String pRabies, String pNotes, final String userID) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CreateAFoundPost.this, "Please Wait",null, true, true);
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
                data.put("species",params[1]);
                data.put("breed",params[2]);
                data.put("markings",params[3]);
                data.put("rabies_tag_num",params[4]);
                data.put("notes",params[5]);
                data.put("photo",params[6]);
                data.put("user_id",params[7]);

                return  ruc.sendPostRequest(FOUND_URL+userID,data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(pName,pSpecies,pBreed,pMarkings,pRabies,pNotes,pPhoto, userID); //Pass bite as a string
    }
}
