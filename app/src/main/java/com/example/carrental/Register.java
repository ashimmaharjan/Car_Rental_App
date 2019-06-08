package com.example.carrental;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.carrental.Interface.CarRentalsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText firstName,lastName,email,username,password,cpassword,phoneNumber;
    Button btnRegister;
    String uFName,uLName,uEmail,uUsername,uPassword,uPhone;
    CarRentalsAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName=findViewById(R.id.inputFName);
        lastName=findViewById(R.id.inputLName);
        email=findViewById(R.id.inputEmail);
        username=findViewById(R.id.inputUName);
        password=findViewById(R.id.inputPass);
        cpassword=findViewById(R.id.inputCPassword);
        phoneNumber=findViewById(R.id.inputPhone);
        btnRegister=findViewById(R.id.register);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(TextUtils.isEmpty(firstName.getText().toString()))
        {
            firstName.setError("Please enter your first name");
            firstName.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(lastName.getText().toString()))
        {
            lastName.setError("Please enter your last name");
            lastName.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(email.getText().toString()))
        {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(username.getText().toString()))
        {
            username.setError("Please enter your username");
            username.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(cpassword.getText().toString()))
        {
            cpassword.setError("Please re-enter your password");
            cpassword.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(phoneNumber.getText().toString()))
        {
            phoneNumber.setError("Please enter your contact number");
            phoneNumber.requestFocus();
            return;
        }
        else if(!password.getText().toString().equals(cpassword.getText().toString()))
        {
            Toast.makeText(this, "Sorry, passwords don't match. Please check and try again.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            uFName=firstName.getText().toString();
            uLName=lastName.getText().toString();
            uEmail=email.getText().toString();
            uUsername=username.getText().toString();
            uPassword=password.getText().toString();
            uPhone=phoneNumber.getText().toString();
            registerUsers();

            firstName.setText("");
            lastName.setText("");
            email.setText("");
            username.setText("");
            password.setText("");
            cpassword.setText("");
            phoneNumber.setText("");

        }
    }

    private void createInstance() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:6969/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(CarRentalsAPI.class);
    }

    private void registerUsers() {
        createInstance();

        Call<String> usersCall=api.addUser(
                uFName,
                uLName,
                uEmail,
                uUsername,
                uPassword,
                uPhone);

        usersCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(Register.this,response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Register.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

