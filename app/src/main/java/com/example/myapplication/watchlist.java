package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class watchlist extends AppCompatActivity implements AdapterView.OnItemClickListener {

//    EditText et_lname;

    ListView list1;


    String companies[]={"RELIANCE INDUSTRIES","TATA CONSULTANCY SERVICE LTD","HDFC BANK LTD","ICICI BANK LTD","HINDUSTHAN UNILEVER LTD","INFOSYS LTD","HOUSING DEVELOPMENT FINANCE CORP.LTD","ITC LTD","STATE BANK OF INDIA","BHARTI AIRTEL LTD","KOTAK MAHINDRA BANK LTD","Bajaj Finance Limited","Life Insurance Corporation of India","LARSEN & TOUBRO LTD","HCL TECHNOLOGIES LTD","ASIAN PAINTS LTD","AXIS BANK LTD","MARUTI SUZUKI INDIA LTD","SUN PHARMACEUTICAL INDUSTRIES LTD","Titan Company Limited","Avenue Supermarts Ltd","ULTRATECH CEMENT LTD","ADANI ENTERPRISES LTD","BAJAJ FINSERV LTD","Oil and Natural Gas Corporation Ltd","WIPRO LTD","NESTLE INDIA LTD","JSW STEEL LTD","POWER GRID CORPORATION OF INDIA LTD","NTPC LTD","TATA MOTORS LTD","Adani Green Energy Ltd","MAHINDRA & MAHINDRA LTD","ADANI PORTS AND SPECIAL ECONOMIC ZONE LTD","COAL INDIA LTD","HINDUSTAN ZINC LTD","TATA STEEL LTD","LTIMindtree Ltd","PIDILITE INDUSTRIES LTD","BAJAJ AUTO LTD","SIEMENS LTD","Adani Transmission Ltd","HDFC Life Insurance Company Ltd","SBI Life Insurance Company Ltd","GRASIM INDUSTRIES LTD","INDIAN OIL CORPORATION LTD","BRITANNIA INDUSTRIES LTD","Adani Total Gas Ltd","Vedanta Limited","DLF LTD"};
    String codes[]={"500325","532540","500180","532174","500696","500209","500010","500875","500112","532454","500247","500034","543526","500510","532281","500820","532215","532500","524715","500114","540376","532538","512599","532978","500312","507685","500790","500228","532898","532555","500570","541450","500520","532921","533278","500188","500470","540005","500331","532977","500550","539254","540777","540719","500300","530965","500825","542066","500295","532868"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

//        et_lname = (EditText) findViewById(R.id.editTextTextPersonName6);
        list1=(ListView) findViewById(R.id.listw1);


        list1.setAdapter(new custom_view_companies(getApplicationContext(),companies,codes));





    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String name=codes[i];


        Intent ii=new Intent(getApplicationContext(),stocks.class);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sh.edit();
        ed.putString("companies", name);
        ed.commit();

        startActivity(ii);





    }
}