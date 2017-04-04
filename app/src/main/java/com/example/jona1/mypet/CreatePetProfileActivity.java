package com.example.jona1.mypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class CreatePetProfileActivity extends AppCompatActivity {

    private EditText petName;
    private EditText species;
    private EditText breed;
    private EditText markings;
    private EditText rabiesTag ;
    private Switch bite;
    private EditText notes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile);
    }

    public void addPetClicked(View v){
        updateInfo();
        String pName = getPetName();
        String pSpecies = getSpecies();
        String pBreed = getBreed();
        String pMarkings = getMarkings();
        String pRabies = getRabies();
        Boolean pBite = getBite();
        String pNotes = getNotes();
    }

    public void updateInfo(){
        this.petName = (EditText) findViewById(R.id.petNameTextView);
        this.species = (EditText) findViewById(R.id.speciesEditText);
        this.breed = (EditText) findViewById(R.id.breedEditText);
        this.markings = (EditText) findViewById(R.id.markingsEditText);
        this.rabiesTag = (EditText) findViewById(R.id.rabiesEditText);
        this.bite = (Switch) findViewById(R.id.biteSwitch);
        this.notes = (EditText) findViewById(R.id.notesSection);
    }

    private String getPetName(){
        return petName.getText().toString();
    }

    private String getSpecies(){
        return species.getText().toString();
    }

    private String getBreed(){
        return breed.getText().toString();
    }

    private String getMarkings(){
        return markings.getText().toString();
    }

    private String getRabies(){
        return rabiesTag.getText().toString();
    }

    private Boolean getBite(){
        return bite.isChecked();
    }

    private String getNotes(){
        return notes.getText().toString();
    }





}
