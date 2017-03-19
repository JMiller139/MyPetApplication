package com.example.jona1.mypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signinClicked(View v){

        if(v.getId() == R.id.signinButton)
        {
            // welcome message with username or email
            EditText a = (EditText)findViewById(R.id.emailEditText);
            String str = a.getText().toString();

            // this line is causing pop out nav bar to not show
            // need to be changed to MainScreenActivity.class but that causes
            // the username to not show in welcome message
            Intent intent = new Intent(this, content_main_screen.class);

            intent.putExtra("Username", str);
            startActivity(intent);
        }
    }

    public void registerClicked(View v){
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        startActivity(intent);
    }
}
