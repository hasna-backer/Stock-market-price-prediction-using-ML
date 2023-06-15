package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class tabs extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        tabLayout=findViewById(R.id.tabb);
        viewPager=findViewById(R.id.viewpager);
        imageView3=findViewById(R.id.imageView3);
        imageView3.setOnClickListener(this);

        tabLayout.setupWithViewPager(viewPager);

        vpadapter vp=new vpadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vp.addfragment(new fragment1(), "Overview");
        vp.addfragment(new fragment2(), "price");
        vp.addfragment(new fragment3(), "prediction");
        viewPager.setAdapter(vp);
    }

    @Override
    public void onClick(View view) {


        startActivity(new Intent(getApplicationContext(),alert.class));

    }
}