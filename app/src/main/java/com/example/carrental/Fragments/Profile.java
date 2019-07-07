package com.example.carrental.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.Interface.CarRentalsAPI;
import com.example.carrental.Model.Users;
import com.example.carrental.R;
import com.example.carrental.UpdateProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }


    TextView firstName,lastName,email,username,phoneNumber;
    Button btnEditProfile;
    String userId,token;
    CarRentalsAPI api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate (R.layout.fragment_profile, container, false);
       firstName=view.findViewById(R.id.displayFName);
       lastName=view.findViewById(R.id.displayLName);
       email=view.findViewById(R.id.displayEmail);
       username=view.findViewById(R.id.displayUsername);
       phoneNumber=view.findViewById(R.id.displayPN);
       btnEditProfile=view.findViewById(R.id.editProfile);

       SharedPreferences preferences=getActivity().getSharedPreferences("UserData",0);
       String uid=preferences.getString("userId",null);
       String tok=preferences.getString("token",null);


       loadUserData();

       btnEditProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent openUpdateProfile=new Intent(getActivity(), UpdateProfile.class);
               startActivity(openUpdateProfile);

           }
       });
       return view;
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

        SharedPreferences preferences=getActivity().getSharedPreferences("UserData",0);
        userId=preferences.getString("uid",null);
        Toast.makeText(getContext(), "User id: "+userId, Toast.LENGTH_SHORT).show();

        final Call<Users> userData=api.userProfile(userId);

        userData.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                Users users=response.body();
                Toast.makeText(getActivity(), "User Data :" +users, Toast.LENGTH_SHORT).show();

                firstName.setText(users.getFirst_name());
                lastName.setText(users.getLast_name());
                email.setText(users.getEmail());
                username.setText(users.getUsername());
                phoneNumber.setText(users.getPhone_number());

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
