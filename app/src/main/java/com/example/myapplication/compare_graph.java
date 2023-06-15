package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.github.farshidroohi.ChartEntity;
import io.github.farshidroohi.LineChart;

public class compare_graph extends AppCompatActivity {
float [] predict;
float [] actual;
String [] dates;
LineChart lc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparee_graph);
//        float [] a = new float[]{113f, 183000f, 18f, 6950f, 3200f, 2300f, 1800f, 1000f, 12000f, 5000f, 33000f};
//        float []  b =  new float[]{0f, 245000f, 1011000f, 100f, 0f, 0f, 47000f, 2000f, 1200f, 124400f, 160000f};
//        String [] legendArr = new String [] {"05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31"};

lc=(LineChart)findViewById(R.id.lineChart2);
lc.setVisibility(View.INVISIBLE);
        dd();
    }
    public void dd(){
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");

        String url = "http://" + hu + ":8000/WMS/compare_price/";

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
                                dates=new String[js.length()];
                                actual=new float[js.length()];
                                predict=new float[js.length()];

                                for(int i=0;i<js.length();i++)

                                {
                                    JSONObject u=js.getJSONObject(i);
                                    dates[i]= u.getString("date");
                                    actual[i]=Float.parseFloat(u.getString("actual"));
                                    predict[i]=Float.parseFloat(u.getString("predict"));
//
//u.getString("p")
                                }


                                ChartEntity firstChartEntity = new ChartEntity(Color.WHITE, actual);
                                ChartEntity secondChartEntity = new ChartEntity(Color.YELLOW, predict);

                                ArrayList<ChartEntity> list = new ArrayList<ChartEntity>();

                                list.add(firstChartEntity);
                                list.add(secondChartEntity);


                                LineChart lineChart = findViewById(R.id.lineChart2);
                                lineChart.setLegend(Arrays.asList(dates));
                                lineChart.setList(list);
                                lc.setVisibility(View.VISIBLE);
                            }

                            else {
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
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();


                params.put("companies", sh.getString("companies",""));
//                params.put("password", pwd);

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