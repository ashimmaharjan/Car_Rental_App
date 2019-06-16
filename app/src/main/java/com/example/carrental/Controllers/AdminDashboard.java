package com.example.carrental.Controllers;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carrental.R;

public class AdminDashboard extends AppCompatActivity {

    Button manageCars, manageUsers, manageBookings;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Car Rentals");
        actionBar.setSubtitle("Admin Dashboard");

        manageCars=findViewById(R.id.manageCars);
        manageUsers=findViewById(R.id.manageUsers);
        manageBookings=findViewById(R.id.manageBookings);

        manageCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCarDashboard=new Intent(AdminDashboard.this, Manage_Cars.class);
                startActivity(openCarDashboard);
            }
        });

    }
}
