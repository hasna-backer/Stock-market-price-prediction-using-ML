package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class basicinfo extends AppCompatActivity {


    TextView tvcompanyname,tvsecurity,tvcurrentvalue,tvindustry,tvpreviousclose,tvprevopen,tvdayhigh,tvdaylow,tvweekheigh,tvweeklow,tvweightedavgprice,tvtotaltradevalue,tvtotaltradedquantity,tvtwoweekavgquantity,tvmarketcapitalfull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basicinfo);


        tvcompanyname= findViewById(R.id.textView48);
        tvsecurity= findViewById(R.id.textView49);
        tvcurrentvalue= findViewById(R.id.textView50);
        tvindustry= findViewById(R.id.textView51);
        tvpreviousclose= findViewById(R.id.textView52);
        tvprevopen= findViewById(R.id.textView53);
        tvdayhigh= findViewById(R.id.textView54);
        tvdaylow= findViewById(R.id.textView55);
        tvweekheigh= findViewById(R.id.textView56);
        tvweeklow= findViewById(R.id.textView57);
        tvweightedavgprice= findViewById(R.id.textView58);
        tvtotaltradevalue= findViewById(R.id.textView59);
        tvtotaltradedquantity= findViewById(R.id.textView60);
        tvtwoweekavgquantity= findViewById(R.id.textView61);
        tvmarketcapitalfull= findViewById(R.id.textView62);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= sh.getString("ip", "");
        String url = "http://" + ip + ":8000/WMS/basicinfo/";
        String lid = sh.getString("lid", "");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


                                JSONObject ja= jsonObj.getJSONObject("data");
                                tvcompanyname.setText(ja.getString("companyName"));
                                tvsecurity.setText(ja.getString("securityID"));
                                tvcurrentvalue.setText(ja.getString("currentValue"));
                                tvindustry.setText(ja.getString("industry"));
                                tvpreviousclose.setText(ja.getString("previousClose"));
                                tvprevopen.setText(ja.getString("previousOpen"));
                                tvdayhigh.setText(ja.getString("dayHigh"));
                                tvdaylow.setText(ja.getString("dayLow"));
                                tvweekheigh.setText(ja.getString("52weekHigh"));
                                tvweeklow.setText(ja.getString("52weekLow"));
                                tvweightedavgprice.setText(ja.getString("weightedAvgPrice"));
                                tvtotaltradevalue.setText(ja.getString("totalTradedValue"));
                                tvtotaltradedquantity.setText(ja.getString("totalTradedQuantity"));
                                tvtwoweekavgquantity.setText(ja.getString("2WeekAvgQuantity"));
                                tvmarketcapitalfull.setText(ja.getString("marketCapFull"));







                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
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

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                params.put("companies", sh.getString("companies",""));//passing to python

                return params;
            }
        };
        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);






    }
}