package com.example.carrental.Controllers;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carrental.R;

public class Manage_Cars extends AppCompatActivity {

    ActionBar actionBar;
    Button btnAddCar,btnUD_Car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__cars);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Manage Cars");

        btnAddCar=findViewById(R.id.addCar);
        btnUD_Car=findViewById(R.id.UD_Car);

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddCar=new Intent(Manage_Cars.this,AddCar.class);
                startActivity(openAddCar);
            }
        });


    }
}
