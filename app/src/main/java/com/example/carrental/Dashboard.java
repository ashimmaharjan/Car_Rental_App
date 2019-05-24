package com.example.carrental;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carrental.Fragments.Bookings;
import com.example.carrental.Fragments.Home;
import com.example.carrental.Fragments.Profile;
import com.example.carrental.Fragments.Search;

public class Dashboard extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tabLayout=findViewById(R.id.tabId);
        viewPager=findViewById(R.id.viewPager);

        ViewPagerFragmentAdapter viewPagerFragmentAdapter=new ViewPagerFragmentAdapter(getSupportFragmentManager());

        viewPagerFragmentAdapter.addFragment(new Home(),"Home");
        viewPagerFragmentAdapter.addFragment(new Search(),"Search");
        viewPagerFragmentAdapter.addFragment(new Bookings(),"Bookings");
        viewPagerFragmentAdapter.addFragment(new Profile(),"Profile");

        viewPager.setAdapter(viewPagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
