package com.example.carrental.Controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.Model.Cars;
import com.example.carrental.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    List<Cars> carsList;
    Context context;
    public static final String BASE_URL= "http://10.0.2.2:6969";

    public CarAdapter(List<Cars> carsList, Context context) {
        this.carsList = carsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View carView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cars_recyclerview,viewGroup,false);
        return new CarViewHolder(carView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder carViewHolder, int i) {

        final Cars cars=carsList.get(i);
        carViewHolder.carName.setText(cars.getCarName());
        carViewHolder.carPrice.setText(cars.getCarRentalPrice());

        Picasso.with(context).load(BASE_URL+"/images/"+cars.getCarImageName()).into(carViewHolder.carImage);
        Log.d("log","onBindHolder: "+BASE_URL+"/images/"+cars.getCarImageName());
        Toast.makeText(context, ""+carsList.size(), Toast.LENGTH_SHORT).show();

        carViewHolder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView carImage;
        public TextView carName,carPrice;
        public Button viewMore;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carImage=itemView.findViewById(R.id.sample_CarImage);
            carName=itemView.findViewById(R.id.sample_CarName);
            carPrice=itemView.findViewById(R.id.sample_CarRPrice);
            viewMore=itemView.findViewById(R.id.sample_btn);
        }
    }
}
