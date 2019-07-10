package com.example.carrental.BBL;

import com.example.carrental.Interface.CarRentalsAPI;
import com.example.carrental.Model.AuthToken;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginBBL {
    private String username;
    private String password;
    boolean isSuccess=false;
    Retrofit retrofit;
    CarRentalsAPI api;

//    public LoginBBL(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    private void createInstance()
//    {
//        retrofit=new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:6969")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        api=retrofit.create(CarRentalsAPI.class);
//    }
//
//    public boolean checkUser()
//    {
//        createInstance();
//        Call<AuthToken>  userCall=api.loginCheck(username,password);
//
//        Response<AuthToken>
//    }
}
