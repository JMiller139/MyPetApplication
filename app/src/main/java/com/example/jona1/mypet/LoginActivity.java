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

            EditText a = (EditText)findViewById(R.id.emailEditText);
            String str = a.getText().toString();

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
