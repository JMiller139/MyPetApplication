package com.example.jona1.mypet;


/**
 * Created by bekal on 4/8/2017.
 */

public class DataPet {
    private String petImageUrl;
    private String petName;
    private String breed;
    private String species;
    private String markings;

    public void SetPetName(String petName) {

        this.petName = petName;
    }
    public void SetBreed(String breed) {

        this.breed = breed;
    }
    public void SetSpecies(String species) {
        this.species = species;
    }
    public void SetMarkings(String markings) {
        this.markings = markings;
    }
    public void SetImageUrl(String petImageUrl){
        this.petImageUrl = petImageUrl;
    }
    public String getPetName(){
        return petName;
    }
    public String getBreed(){
        return breed;
    }
    public String getSpecies(){
        return species;
    }
    public String getMarkings(){
        return markings;
    }
    public String getPetImageUrl(){
        return petImageUrl;
    }
}