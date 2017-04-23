package com.example.jona1.mypet;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class content_main_screen extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_screen);

        Intent intent = getIntent();

        String username = intent.getStringExtra(LoginActivity.USER_NAME);


        // Jonathan "welcome back" will need to be converted to a string resource to solve the warning
        // I forgot how to do it
        textView.setText("Welcome back, "+username);
    }

}