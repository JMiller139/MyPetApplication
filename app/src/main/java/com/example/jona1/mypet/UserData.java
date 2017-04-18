package com.example.jona1.mypet;

/**
 * Created by bekal on 4/12/2017.
 */

public class UserData {

    private String usrImage;
    private String fname;
    private String lname;
    private String username;
    private String email;
    private String userId;
    private String address;

    public UserData(){

    }

    public String getImage() {
        return usrImage;
    }

    public void setImage(String image) {
        this.usrImage = image;
    }

    public String getFName() {
        return fname;
    }

    public void getFName(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsrImage(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
}
