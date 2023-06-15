package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Viewalaram extends AppCompatActivity {
    ListView lvs;
    String [] stock,price,id;


    String companies[]={"RELIANCE INDUSTRIES","TATA CONSULTANCY SERVICE LTD","HDFC BANK LTD","ICICI BANK LTD","HINDUSTHAN UNILEVER LTD","INFOSYS LTD","HOUSING DEVELOPMENT FINANCE CORP.LTD","ITC LTD","STATE BANK OF INDIA","BHARTI AIRTEL LTD","KOTAK MAHINDRA BANK LTD","Bajaj Finance Limited","Life Insurance Corporation of India","LARSEN & TOUBRO LTD","HCL TECHNOLOGIES LTD","ASIAN PAINTS LTD","AXIS BANK LTD","MARUTI SUZUKI INDIA LTD","SUN PHARMACEUTICAL INDUSTRIES LTD","Titan Company Limited","Avenue Supermarts Ltd","ULTRATECH CEMENT LTD","ADANI ENTERPRISES LTD","BAJAJ FINSERV LTD","Oil and Natural Gas Corporation Ltd","WIPRO LTD","NESTLE INDIA LTD","JSW STEEL LTD","POWER GRID CORPORATION OF INDIA LTD","NTPC LTD","TATA MOTORS LTD","Adani Green Energy Ltd","MAHINDRA & MAHINDRA LTD","ADANI PORTS AND SPECIAL ECONOMIC ZONE LTD","COAL INDIA LTD","HINDUSTAN ZINC LTD","TATA STEEL LTD","LTIMindtree Ltd","PIDILITE INDUSTRIES LTD","BAJAJ AUTO LTD","SIEMENS LTD","Adani Transmission Ltd","HDFC Life Insurance Company Ltd","SBI Life Insurance Company Ltd","GRASIM INDUSTRIES LTD","INDIAN OIL CORPORATION LTD","BRITANNIA INDUSTRIES LTD","Adani Total Gas Ltd","Vedanta Limited","DLF LTD"};
    String codes[]={"500325","532540","500180","532174","500696","500209","500010","500875","500112","532454","500247","500034","543526","500510","532281","500820","532215","532500","524715","500114","540376","532538","512599","532978","500312","507685","500790","500228","532898","532555","500570","541450","500520","532921","533278","500188","500470","540005","500331","532977","500550","539254","540777","540719","500300","530965","500825","542066","500295","532868"};
;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewalaram);

        lvs= findViewById(R.id.lvs);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String maclis=sh.getString("mac_list","");
        String uid=sh.getString("uid","");
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":8000/WMS/viewreminder/";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");
                                stock=new String[js.length()];
                                price=new String[js.length()];
                                id=new String[js.length()];


                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    stock[i]= u.getString("stock");
                                    price[i]=u.getString("price");
                                    id[i]=u.getString("id");

                                }

                                lvs.setAdapter(new custom_view_reminder(getApplicationContext(),stock,price,id));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                String id=sh.getString("lid","");
                params.put("lid",id);
                return params;
            }
        };
        int MY_SOCKET_TIMEOUT_MS=100000;
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }
}