package com.example.carrental.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.carrental.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bookings extends Fragment {


    public Bookings() {
        // Required empty public constructor
    }


    static EditText pickUpDate,dropOffDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bookings, container, false);
        pickUpDate=view.findViewById(R.id.inputPickUpDate);
        dropOffDate=view.findViewById(R.id.inputDropOffDate);


        pickUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DatePickerFragment datePickerFragment=new DatePickerFragment();
              datePickerFragment.setFlag(DatePickerFragment.FLAG_PICKUP_DATE);
              datePickerFragment.show(getFragmentManager(),"datePicker");
            }
        });

        dropOffDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatePickerFragment datePickerFragment=new DatePickerFragment();
               datePickerFragment.setFlag(DatePickerFragment.FLAG_DROPOFF_DATE);
               datePickerFragment.show(getFragmentManager(),"datePicker");
            }
        });


        return view;
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        public static final int FLAG_PICKUP_DATE=0;
        public static final int FLAG_DROPOFF_DATE=1;

        private int flag=0;
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            final Calendar c=Calendar.getInstance();
            int year= c.get(Calendar.YEAR);
            int month= c.get(Calendar.MONTH);
            int day= c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),this,year,month,day);
        }
        
        public void setFlag(int i)
        {
            flag=i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            if (flag==FLAG_PICKUP_DATE)
            {
                pickUpDate.setText(day+"/"+(month+1)+"/"+year);
            }
            else if(flag==FLAG_DROPOFF_DATE)
            {
                dropOffDate.setText(day+"/"+(month+1)+"/"+year);
            }
        }
    }

}
