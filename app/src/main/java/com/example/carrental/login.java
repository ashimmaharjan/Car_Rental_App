package com.example.carrental;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener {
    EditText username,password;
    Button btnLogin,btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.inputUsername);
        password=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.login);
        btnSignUp=findViewById(R.id.signUp);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.login:
                if(TextUtils.isEmpty(username.getText().toString()))
                {
                    username.setError("Please enter your username");
                    username.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else
                {
                    Intent dashboard=new Intent(login.this,Dashboard.class);
                    startActivity(dashboard);
                }
                break;

            case R.id.signUp:
                Intent openRegister=new Intent(login.this,register.class);
                startActivity(openRegister);
                break;
        }
    }
}
