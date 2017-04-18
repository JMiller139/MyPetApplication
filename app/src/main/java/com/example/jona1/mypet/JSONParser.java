package com.example.jona1.mypet;

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
    private JSONArray lostPets = null;

    List<DataPet> LostPets;

    private String json;

    public JSONParser(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject = null;

        try{
            lostPets = new JSONArray(json);
            petNames = new String[lostPets.length()];
            breeds = new String[lostPets.length()];
            species = new String[lostPets.length()];
            markings = new String[lostPets.length()];

            LostPets = new ArrayList<DataPet>();

            for(int i=0; i<lostPets.length();++i){
                DataPet pet_object = new DataPet();

                jsonObject = lostPets.getJSONObject(i);

                petNames[i] = jsonObject.getString("petName");
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
    List<DataPet> getLostPets(){
        return LostPets;
    }
}
