package com.example.carrental.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carrental.Manage_Cars;
import com.example.carrental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }

    Button manageCars, manageUsers, manageBookings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        manageCars=view.findViewById(R.id.manageCars);
        manageUsers=view.findViewById(R.id.manageUsers);
        manageBookings=view.findViewById(R.id.manageBookings);

        manageCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCarDashboard=new Intent(getActivity(), Manage_Cars.class);
                startActivity(openCarDashboard);
            }
        });

        return view;
    }

}
