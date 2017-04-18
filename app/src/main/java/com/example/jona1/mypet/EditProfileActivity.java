package com.example.jona1.mypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.Log;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private String fname;
    private String lname;
    private String address;
    private String email;

    private static String TAG = "testingMessage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        buttonRegister = (Button) findViewById(R.id.saveChangesButton);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){

        }
    }
}
