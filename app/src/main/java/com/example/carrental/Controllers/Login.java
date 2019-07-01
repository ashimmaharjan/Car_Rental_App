package com.example.carrental.Controllers;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Broadcast.BroadcastReceiver;
import com.example.carrental.Interface.CarRentalsAPI;
import com.example.carrental.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver(this);

    EditText username,password;
    Button btnLogin,btnSignUp;
    CarRentalsAPI api;

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

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:6969/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(CarRentalsAPI.class);
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
                    if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                    {
                        Intent openAdminDashboard=new Intent(Login.this, AdminDashboard.class);
                        startActivity(openAdminDashboard);

                    }
                    else
                    {
                        Call<String> checkLogin=api.loginCheck(username.getText().toString(),password.getText().toString());
                        checkLogin.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.body().equals("Login successful")){
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent openDashboard=new Intent(Login.this,Dashboard.class);
                                    startActivity(openDashboard);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(Login.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;

            case R.id.signUp:
                Intent openRegister=new Intent(Login.this, Register.class);
                startActivity(openRegister);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }
}
