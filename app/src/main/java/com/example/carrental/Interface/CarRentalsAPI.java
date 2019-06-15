package com.example.carrental.Interface;

import android.telephony.SignalStrength;

import com.example.carrental.Model.Users;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CarRentalsAPI {

    @FormUrlEncoded
    @POST("registerUser")
    Call<String> addUser(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email")String email, @Field("username") String username, @Field("password") String password, @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("login")
    Call<String> loginCheck(@Field("username") String username,@Field("password") String password);

    @FormUrlEncoded
    @POST("addCar")
    Call<String> addCar(@Field("carName") String carName,@Field("carMan") String carMan,@Field("carAC_Status") String carAC_Status,@Field("carSeats") String carSeats,
                        @Field("carMileage") String carMileage,@Field("carRentalPrice") String carRentalPrice,@Field("carImageName") String carImageName);

    @Multipart
    @POST("uploadImage")
    Call<String> uploadImage(@Part MultipartBody.Part multipartbody);
}
