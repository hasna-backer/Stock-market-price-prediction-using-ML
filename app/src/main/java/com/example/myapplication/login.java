package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity implements View.OnClickListener {
    EditText et_usr;
    EditText et_pwd;
    Button bt_login;
    TextView tv_register;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smaple);
        et_usr = (EditText) findViewById(R.id.editTextTextPersonName);
        et_pwd = (EditText) findViewById(R.id.editTextTextPersonName2);
        tv_register=(TextView) findViewById(R.id.textView3);
        bt_login = (Button) findViewById(R.id.button);
        bt_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);



        et_usr.setText("abida@gmail.com");
        et_pwd.setText("Abida@123");


    }

    @Override
    public void onClick(View view) {
        if(view==tv_register){
            startActivity(new Intent(getApplicationContext(),signup.class));
        }

        else {
        final String username = et_usr.getText().toString();
        final String pwd = et_pwd.getText().toString();

        if (username.length() == 0) {
            et_usr.setError("Type Username");
            et_usr.requestFocus();

        } else if (pwd.length() == 0) {
            et_pwd.setError("Type Password");
            et_pwd.requestFocus();

        } else {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");

            String url = "http://" + hu + ":8000/WMS/login_user/";

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
                                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("lid", jsonObj.getString("lid"));
//                                    ed.putString("lid",jsonObj.getString("status"));
                                    ed.commit();
                                    Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();


                                    if (jsonObj.getString("type").equalsIgnoreCase("user")) {

                                        startService(new Intent(getApplicationContext(),Notify.class));
                                        Intent it = new Intent(getApplicationContext(), MainActivity2.class);
                                        startActivity(it);
                                    }  else {
                                        Toast.makeText(getApplicationContext(), "Not user +++++++ check login page", Toast.LENGTH_LONG).show();

                                        Intent it = new Intent(getApplicationContext(), login.class);
                                        startActivity(it);
                                    }
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


                    params.put("username", username);
                    params.put("password", pwd);

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
    }

}