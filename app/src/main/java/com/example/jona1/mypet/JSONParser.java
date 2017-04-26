package com.example.jona1.mypet;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bekal on 4/16/2017.
 */

public class JSONParser {

    public static String[] petNames;
    public static String[] breeds;
    public static String[] species;
    public static String[] markings;
    private final String TAG = "test";
    private JSONArray userPets = null;

    private List<DataPet> UserPets;

    private JSONObject jsonObject;

    private String response;

    public JSONParser(String response){
        this.response = response;
    }

    protected void parseJSON(){

        Log.i(TAG,"Before Try");
        try{
            jsonObject = new JSONObject(response);
            Log.i(TAG,response);
            userPets = jsonObject.getJSONArray("result");
            petNames = new String[userPets.length()];
            breeds = new String[userPets.length()];
            species = new String[userPets.length()];
            markings = new String[userPets.length()];

            UserPets = new ArrayList<DataPet>();

            for(int i=0; i<userPets.length();++i){
                DataPet pet_object = new DataPet();

                jsonObject = userPets.getJSONObject(i);
                Log.i(TAG,"after initalizing");

                petNames[i] = jsonObject.getString("name");
                breeds[i] = jsonObject.getString("breed");
                species[i] = jsonObject.getString("species");
                markings[i] = jsonObject.getString("markings");

                pet_object.SetPetName(petNames[i]);
                pet_object.SetBreed(breeds[i]);
                pet_object.SetSpecies(species[i]);
                pet_object.SetMarkings(markings[i]);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    List<DataPet> getPets(){
        return UserPets;
    }
}