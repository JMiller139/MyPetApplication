package com.example.jona1.mypet;


        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import java.util.HashMap;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextAddress;


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

        register(username,password,email,fname,lname,address);
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


}