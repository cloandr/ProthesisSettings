package com.despe.appprot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class PatientFile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MSG_DEBUG", "[PatientFile] onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_file);

        //3 - Configure ViewPager
        this.configureViewPagerAndTabs();
    }

    private void configureViewPagerAndTabs(){
        // 1 - Get ViewPager from layout
        ViewPager pager = (ViewPager)findViewById(R.id.activity_patient_file_viewpager);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        // 3 - Get TabLayout from layout
        TabLayout tabs= (TabLayout)findViewById(R.id.activity_patient_file_tabs);
        // 4 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 5 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
