package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class change_password extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    EditText et_old;
    EditText et_new;
    EditText et_confirm;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        et_old = (EditText) findViewById(R.id.editTextTextPersonName10);
        et_new = (EditText) findViewById(R.id.editTextTextPersonName8);
        et_confirm = (EditText) findViewById(R.id.editTextTextPersonName9);
        et_confirm.addTextChangedListener(this);
        btn = (Button) findViewById(R.id.button4);
        btn.setOnClickListener(this);

    }
        @Override
        public void onClick (View view) {

            final String old = et_old.getText().toString();
            final String newp = et_new.getText().toString();
            final String cpwd = et_confirm.getText().toString();


            if (old.length() == 0) {
                et_old.setError("Enter old password");
                et_old.requestFocus();
            } else if (newp.length() == 0) {
                et_new.setError("Enter new password");
                et_new.requestFocus();
            } else if (!newp.matches(("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$"))) {
                et_new.setError("Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character");
                et_new.requestFocus();
            } else if (cpwd.length() == 0) {
                et_confirm.setError("Please confirm your password");
                et_confirm.requestFocus();

            } else if (!cpwd.equals(newp)) {
                et_confirm.setError("Password missmatch");
                et_confirm.requestFocus();

            } else {


                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String hu = sh.getString("ip", "");

                String url = "http://" + hu + ":8000/WMS/user_cp_post/";

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

                                        Intent i = new Intent(getApplicationContext(), login.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                                    }
                                    else if (jsonObj.getString("status").equalsIgnoreCase("no")) {


                                        Toast.makeText(getApplicationContext(), "Invalid Old Password", Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
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

                        params.put("old", old);
                        params.put("newp", newp);
                        params.put("cpwd", cpwd);
                    params.put("lid", sh.getString("lid",""));

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


        @Override
        public void beforeTextChanged (CharSequence charSequence,int i, int i1, int i2){

        }

        @Override
        public void onTextChanged (CharSequence charSequence,int i, int i1, int i2){
            if (!et_confirm.getText().toString().equalsIgnoreCase(et_new.getText().toString())) {
                et_confirm.setError("Missmatch");
            }
        }

        @Override
        public void afterTextChanged (Editable editable){

        }

}