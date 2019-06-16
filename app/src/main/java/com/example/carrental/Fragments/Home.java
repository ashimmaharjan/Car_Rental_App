package com.example.carrental.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.carrental.Controllers.CarAdapter;
import com.example.carrental.Controllers.Manage_Cars;
import com.example.carrental.Interface.CarRentalsAPI;
import com.example.carrental.Model.Cars;
import com.example.carrental.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    RecyclerView recyclerView;
    Retrofit retrofit;
    CarRentalsAPI api;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);

        getCars();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        return view;
    }

    public void getCars()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:6969")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(CarRentalsAPI.class);

        Call<List<Cars>> listCall=api.getCars();
        listCall.enqueue(new Callback<List<Cars>>() {
            @Override
            public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {

                List<Cars> carsList=response.body();
                recyclerView.setAdapter(new CarAdapter(carsList,getActivity()));
            }

            @Override
            public void onFailure(Call<List<Cars>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
