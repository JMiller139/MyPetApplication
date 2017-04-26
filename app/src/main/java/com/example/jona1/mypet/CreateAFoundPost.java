package com.example.jona1.mypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateAFoundPost extends AppCompatActivity {

    private String pName;
    private String pSpecies;
    private String pBreed;
    private String pMarkings;
    private String pNotes;
    private String pPhoto;
    private String pRabies;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_afound_post);
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
    }
}
