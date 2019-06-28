package com.example.carrental.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.carrental.R;

public class UpdateDeleteCar extends AppCompatActivity {

    EditText searchCar,carName,carMan,carSeats,carMileage,carRPrice;
    Button btnSearch,btnUpdate,btnDelete;
    RadioGroup AC_Status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_car);

        searchCar=findViewById(R.id.etSearch);
        carName=findViewById(R.id.fillCarName);
        carMan=findViewById(R.id.fillCarMan);
        carSeats=findViewById(R.id.fillCarSeats);
        carMileage=findViewById(R.id.fillCarMileage);
        carRPrice=findViewById(R.id.fillCarPrice);

        btnSearch=findViewById(R.id.searchCar);
        btnUpdate=findViewById(R.id.updateCar);
        btnDelete=findViewById(R.id.deleteCar);
    }
}
