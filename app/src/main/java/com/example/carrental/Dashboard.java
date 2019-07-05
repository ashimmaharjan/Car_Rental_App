package com.example.carrental;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carrental.Controllers.ViewPagerFragmentAdapter;
import com.example.carrental.Fragments.Bookings;
import com.example.carrental.Fragments.Home;
import com.example.carrental.Fragments.Profile;
import com.example.carrental.Fragments.Search;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {


    public Dashboard() {
        // Required empty public constructor
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_dashboard,container,false);

        tabLayout=view.findViewById(R.id.tabId);
        viewPager=view.findViewById(R.id.viewPager);

        ViewPagerFragmentAdapter viewPagerFragmentAdapter=new ViewPagerFragmentAdapter(getChildFragmentManager());

        viewPagerFragmentAdapter.addFragment(new Home(),"Home");
        viewPagerFragmentAdapter.addFragment(new Search(),"Search");
        viewPagerFragmentAdapter.addFragment(new Bookings(),"Bookings");
        viewPagerFragmentAdapter.addFragment(new Profile(),"Profile");

        viewPager.setAdapter(viewPagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_book_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_person_black_24dp);

       return view;
    }

}
