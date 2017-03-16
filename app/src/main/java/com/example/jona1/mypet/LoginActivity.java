package com.example.jona1.mypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signinClicked(View v){
        Intent intent = new Intent();
    }

    public void registerClicked(View v){
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        startActivity(intent);
    }
}
