package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ip extends AppCompatActivity implements View.OnClickListener {
    EditText ed_ip;
    Button bt_cnct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        ed_ip = (EditText) findViewById(R.id.editTextTextPersonName3);
        bt_cnct=(Button) findViewById(R.id.button2);
        bt_cnct.setOnClickListener(this);


        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed_ip.setText(sh.getString("ip",""));
    }

    @Override
    public void onClick(View view) {
        final String ip_address=ed_ip.getText().toString();

        if (ip_address.length()==0){
            ed_ip.setError("Missing");
        }
        else {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", ip_address);
            ed.commit();
            Intent it = new Intent(getApplicationContext(), login.class);
            startActivity(it);

        }

    }
}