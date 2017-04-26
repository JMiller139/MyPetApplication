package com.example.jona1.mypet;


// saustin4
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        //import android.view.Menu;
        //import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        //import java.io.BufferedReader;
        //import java.io.InputStreamReader;
        //import java.io.UnsupportedEncodingException;
        //import java.net.HttpURLConnection;
        //import java.net.URL;
        //import java.net.URLEncoder;
        import java.util.HashMap;
        //import java.util.Map;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextAddress;
    private EditText editTextConfirmPassword;
    private EditText editTextConfirmEmail;

    private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    private Button buttonRegister;

    private static final String REGISTER_URL = "https://php.radford.edu/~team04/userRegistration/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        editTextUsername = (EditText) findViewById(R.id.usernameEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordEditText);
        editTextEmail = (EditText) findViewById(R.id.emailEditText);
        editTextFname = (EditText) findViewById(R.id.fnameEditText);
        editTextLname = (EditText) findViewById(R.id.lnameEditText);
        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirmPasswordEditText);
        editTextConfirmEmail = (EditText) findViewById(R.id.confirmEmailEditText);

        buttonRegister = (Button) findViewById(R.id.createAccountButton);

        buttonRegister.setOnClickListener(this);
    }
    //---
    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String fname = editTextFname.getText().toString().trim().toLowerCase();
        String lname = editTextLname.getText().toString().trim().toLowerCase();
        String address = editTextAddress.getText().toString().trim().toLowerCase();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim().toLowerCase();
        String confirmEmail = editTextConfirmEmail.getText().toString().trim().toLowerCase();
        if(email.matches(confirmEmail)){

            if(email.matches(EMAIL_PATTERN)) {

                if (password.matches(confirmPassword)) {
                    register(username,password,email,fname,lname,address);
                } else { Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_LONG).show();}

            }else{Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show();}

        }else{ Toast.makeText(this, "Emails Do Not Match",Toast.LENGTH_LONG).show();}

    }

    private void register(String username, String password, String email, String fname, String lname, String address) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            private ProgressDialog loading;
            private RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterAccountActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("fname",params[0]);
                data.put("lname",params[1]);
                data.put("address",params[2]);
                data.put("username",params[3]);
                data.put("password",params[4]);
                data.put("email",params[5]);

                return  ruc.sendPostRequest(REGISTER_URL,data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(fname,lname,address,username,password,email);
    }

    public void onCreateAccountClick(View view) {
    }
}