package com.example.carrental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.Fragments.Profile;
import com.example.carrental.Interface.CarRentalsAPI;
import com.example.carrental.Model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateProfile extends AppCompatActivity {

    TextView firstName,lastName,email,username,phoneNumber;
    Button btnUpdateProfile;
    String userId,token;
    CarRentalsAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firstName=findViewById(R.id.inputFName);
        lastName=findViewById(R.id.inputLName);
        email=findViewById(R.id.inputEmail);
        username=findViewById(R.id.inputUName);
        phoneNumber=findViewById(R.id.inputPhone);
        btnUpdateProfile=findViewById(R.id.updateProfile);

        loadUserData();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void loadUserData()
    {
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:6969/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api=retrofit.create(CarRentalsAPI.class);

        SharedPreferences preferences=getSharedPreferences("UserData",0);
        userId=preferences.getString("uid",null);
        Toast.makeText(UpdateProfile.this, "User id: "+userId, Toast.LENGTH_SHORT).show();

        final Call<Users> userData=api.userProfile(userId);

        userData.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                Users users=response.body();
                firstName.setText(users.getFirst_name());
                lastName.setText(users.getLast_name());
                email.setText(users.getEmail());
                username.setText(users.getUsername());
                phoneNumber.setText(users.getPhone_number());

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Failure: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void updateProfile()
    {
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:6969/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api=retrofit.create(CarRentalsAPI.class);

        String newFirstName,newLastName,newEmail,newUsername,newPhoneNumber;

        newFirstName=firstName.getText().toString();
        newLastName=lastName.getText().toString();
        newEmail=email.getText().toString();
        newUsername=username.getText().toString();
        newPhoneNumber=phoneNumber.getText().toString();

        SharedPreferences preferences=(UpdateProfile.this).getSharedPreferences("UserData",0);
        String userId=preferences.getString("uid",null);

        Toast.makeText(this, "User id: +" +userId, Toast.LENGTH_SHORT).show();

        Call<String> updateProfileData=api.updateProfile(userId,newFirstName,newLastName,newEmail,newUsername,newPhoneNumber);
        updateProfileData.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
