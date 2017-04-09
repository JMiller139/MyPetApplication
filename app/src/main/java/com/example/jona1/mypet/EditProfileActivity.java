package com.example.jona1.mypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.sql.*;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private String fname;
    private String lname;
    private String address;
    private String email;


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
            String url = "jdbc:mysql://10.0.2.2/test";


            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection conn = DriverManager.getConnection(url, "Team04", "team04");
                String query = "SELECT * FROM users WHERE user_id = 1";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next())
                {
                    fname = rs.getString("fname");
                    lname = rs.getString("lname");
                    address = rs.getString("address");
                    email = rs.getString("email");

                    EditText ed = (EditText) findViewById (R.id.changeFName);
                    ed.setText(fname);
                    ed = (EditText) findViewById (R.id.changeLName);
                    ed.setText(fname);
                    ed = (EditText) findViewById (R.id.changeEmail);
                    ed.setText(fname);
                    ed = (EditText) findViewById (R.id.changeAddress);
                    ed.setText(fname);
                }
                st.close();

            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e1) {
                e1.printStackTrace();
            }
        }
    }
}
