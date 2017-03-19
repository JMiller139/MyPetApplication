package com.example.jona1.mypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
    }
// @author Stacy Austin
    public void onCreateAccountClick(View v)
    {
        if(v.getId()==R.id.createAccountButton)
        {
            EditText uname = (EditText) findViewById(R.id.usernameEditText);
            EditText email1 = (EditText) findViewById(R.id.emailEditText);
            EditText email2 = (EditText) findViewById(R.id.confirmEmailEditText);
            EditText pass1 = (EditText) findViewById(R.id.passwordEditText);
            EditText pass2 = (EditText) findViewById(R.id.confrimPasswordEditText);

            String unamestr = uname.getText().toString();
            String email1str = email1.getText().toString();
            String email2str = email2.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            if(!pass1str.equals(pass2str))
            {
                //popup message
                Toast pass = Toast.makeText(RegisterAccountActivity.this,
                        "passwords do not match!", Toast.LENGTH_SHORT);
                pass.show();
            }

            if(!email1str.equals(email2str))
            {
                //popup message
                Toast email = Toast.makeText(RegisterAccountActivity.this,
                        "emails do not match!", Toast.LENGTH_SHORT);
                email.show();
            }
        }
    }

}
