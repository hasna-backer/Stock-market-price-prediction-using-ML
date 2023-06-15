package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
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

public class view_profile extends AppCompatActivity {
    TextView name,gender,dob,email,phno,edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        name=(TextView) findViewById(R.id.textView5);
        gender=(TextView) findViewById(R.id.textView23);
        dob=(TextView) findViewById(R.id.textView21);

        email=(TextView) findViewById(R.id.textView15);
        phno=(TextView) findViewById(R.id.textView17);
        edit=(TextView) findViewById(R.id.textView24);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ij=new Intent(getApplicationContext(),update_profile.class);
                startActivity(ij);
            }
        });




        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= sh.getString("ip", "");
        String url = "http://" + ip + ":8000/WMS/view_profile/";
        String lid = sh.getString("lid", "");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                JSONObject jj = jsonObj.getJSONObject("data");
//                                JSONObject pic = jsonObj.getString("status");

                                name.setText(jsonObj.getString("fname")+" "+jsonObj.getString("lname"));
                                dob.setText(jsonObj.getString("dob"));

                                gender.setText(jsonObj.getString("gender"));
                                email.setText(jsonObj.getString("email"));
                                phno.setText(jsonObj.getString("phon"));





                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip = sh.getString("ip", "");
//                                String url = "http://" + ip + ":4000" + image;
//                                Picasso.with(getApplicationContext()).load(url).into(pic);
//                                Picasso.with(getApplicationContext()).load(url).into(image);//circle

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

                params.put("lid", lid);//passing to python

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
