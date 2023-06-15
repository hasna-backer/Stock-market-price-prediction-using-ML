package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    ImageView improfile,imcompanies,imbookmarks,imalaram,imnews,imnotification,imchangepassword,imlogout,imcomplaints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindash);


        improfile=(ImageView) findViewById(R.id.profile);
        imcompanies=(ImageView) findViewById(R.id.companies);
        imbookmarks=(ImageView) findViewById(R.id.bookmarks);
        imalaram=(ImageView) findViewById(R.id.alaram);
        imnews=(ImageView) findViewById(R.id.news);
        imnotification=(ImageView) findViewById(R.id.notification);
        imchangepassword=(ImageView) findViewById(R.id.changepassword);
        imlogout=(ImageView) findViewById(R.id.logout);
        imcomplaints=(ImageView) findViewById(R.id.Complaints);


        improfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ins= new Intent(getApplicationContext(),view_profile.class);
                startActivity(ins);
            }
        });

        imcomplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ins= new Intent(getApplicationContext(),view_reply.class);
                startActivity(ins);
            }
        });


        imcompanies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(getApplicationContext(),watchlist.class);
                startActivity(in);
            }
        });


        imbookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),userviewbookmarks.class);
                startActivity(i);
            }
        });


        imalaram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),Viewalaram.class);
                startActivity(i);
            }
        });



        imchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),change_password.class);
                startActivity(i);
            }
        });


        imlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),login.class);
                startActivity(i);
            }
        });


        imnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),Viewnotification.class);
                startActivity(i);
            }
        });



        imnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),news.class);
                startActivity(i);
            }
        });





    }
}