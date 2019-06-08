package com.example.carrental.Interface;

import android.telephony.SignalStrength;

import com.example.carrental.Model.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CarRentalsAPI {

    @FormUrlEncoded
    @POST("registerUser")
    Call<String> addUser(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email")String email, @Field("username") String username, @Field("password") String password, @Field("phone_number") String phone_number);


}
