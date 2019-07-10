package com.example.carrental.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Broadcast.BroadcastReceiver;

import com.example.carrental.Dashboard;
import com.example.carrental.Interface.CarRentalsAPI;
import com.example.carrental.Model.AuthToken;
import com.example.carrental.R;
import com.example.carrental.UserNavigationDrawer;

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
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private SensorManager sensorManager;

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
        proximity();

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
                    Vibrator vibrator=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(400);
                    return;
                }
                else if(TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError("Please enter your password");
                    password.requestFocus();
                    Vibrator vibrator=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(400);
                    return;
                }
                else
                {
                    if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                    {
                        Intent openAdminDashboard=new Intent(Login.this, AdminDashboard.class);
                        startActivity(openAdminDashboard);
                    }
                    else if (username.getText().toString().equals("test") && password.getText().toString().equals("test"))
                    {
                        Intent openUserDashboard=new Intent(Login.this, UserNavigationDrawer.class);
                        startActivity(openUserDashboard);
                    }
                    else
                    {
                        Call<AuthToken> checkLogin=api.loginCheck(username.getText().toString(),password.getText().toString());
                        checkLogin.enqueue(new Callback<AuthToken>() {
                            @Override
                            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                                if(!response.isSuccessful())
                                {
                                    Toast.makeText(Login.this, "Error"+response.code(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                                preferences=(Login.this).getSharedPreferences("UserData",0);
                                editor=preferences.edit();
                                AuthToken data=response.body();
                                editor.putString("token",data.getToken());
                                editor.putString("uid",data.getUsers().get_id());
                                Toast.makeText(Login.this, "User id is : " + data.getUsers().get_id(), Toast.LENGTH_SHORT).show();
                                editor.commit();
                                Toast.makeText(Login.this, "Login successful" + response.body().getToken(), Toast.LENGTH_SHORT).show();
                                Intent openDashboard=new Intent(Login.this,UserNavigationDrawer.class);
                                startActivity(openDashboard);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<AuthToken> call, Throwable t) {
                                Toast.makeText(Login.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void proximity()
    {
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sensor == null)
        {
            Toast.makeText(this, "No sensor detected", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Sensor Kicking in .....", Toast.LENGTH_SHORT).show();
        }

        SensorEventListener proximityListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                WindowManager.LayoutParams params = Login.this.getWindow().getAttributes();
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] == 0) {
                        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        params.screenBrightness = 0;
                        getWindow().setAttributes(params);
                    } else {
                        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        params.screenBrightness = -1f;
                        getWindow().setAttributes(params);
                    }
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(proximityListener,sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }
}
