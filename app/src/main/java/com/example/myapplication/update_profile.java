package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class update_profile extends AppCompatActivity implements View.OnClickListener {
    EditText et_lname;
    EditText et_fname;
    EditText et_dob;
    RadioButton male, female, others;
    EditText et_email;
    EditText et_pwd;
    EditText et_phone;
    EditText et_cpwd;
    Button bt_signup;


    String gender = "";


    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        et_lname = (EditText) findViewById(R.id.editTextTextPersonName6);
        et_fname = (EditText) findViewById(R.id.editTextTextPersonName5);
        et_dob = (EditText) findViewById(R.id.editTextDate);
        male = findViewById(R.id.radioButton);
        female = findViewById(R.id.radioButton2);
        others = findViewById(R.id.radioButton3);
        et_email = (EditText) findViewById(R.id.editTextTextPersonName7);
        et_phone = (EditText) findViewById(R.id.editTextTextPersonName4);

        bt_signup = (Button) findViewById(R.id.button4);
        bt_signup.setOnClickListener(this);


    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    String ip = sh.getString("ip", "");
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

                            et_fname.setText(jsonObj.getString("fname"));
                            et_lname.setText(jsonObj.getString("lname"));
                            et_dob.setText(jsonObj.getString("dob"));

                            //  gender.setText(jsonObj.getString("gender"));
                            et_email.setText(jsonObj.getString("email"));
                            et_phone.setText(jsonObj.getString("phon"));
                            if (jsonObj.getString("gender").equalsIgnoreCase("male")) {
                                male.setChecked(true);
                            } else if (jsonObj.getString("gender").equalsIgnoreCase("female")) {
                                female.setChecked(true);
                            } else {
                                others.setChecked(true);
                            }


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

        postRequest.setRetryPolicy(new

    DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);




//        ################   datepicker   ########################

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        }
    };
        et_dob.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new DatePickerDialog(update_profile.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });
}

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        et_dob.setText(dateFormat.format(myCalendar.getTime()));
    }




    @Override
    public void onClick(View view) {
        final String fname = et_fname.getText().toString();
        final String lname = et_lname.getText().toString();
        final String dob = et_dob.getText().toString();
        final String email = et_email.getText().toString();

        final String phone = et_phone.getText().toString();

        if (fname.length() == 0) {
            et_fname.setError("Enter First Name");
            et_fname.requestFocus();

        } else if (!fname.matches("^[a-z,A-Z]*$")) {
            et_fname.setError("Characters Allowed");
            et_fname.requestFocus();
        } else if (lname.length() == 0) {
            et_lname.setError("Enter Last Name");
            et_dob.requestFocus();

        } else if (!lname.matches("^[a-z,A-Z]*$")) {
            et_lname.setError("Characters Allowed");
            et_lname.requestFocus();
        } else if (dob.length() == 0) {
            et_dob.setError("Missing");
            et_dob.requestFocus();

        } else if (dob.length() != 8) {
            et_dob.setError("Invalid dob");
            et_dob.requestFocus();
        } else if (email.length() == 0) {
            et_email.setError("Enter Email ");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter valid email");
            et_email.requestFocus();
        } else if (phone.length() == 0) {
            et_phone.setError("Enter Phone number");
        } else if (phone.length() != 10) {
            et_phone.setError("Invalid mobile");
            et_phone.requestFocus();
        }
        if (male.isChecked() == true) {
            gender = "male";
        } else if (female.isChecked() == true) {
            gender = "female";
        } else {
            gender = "Others";
        }


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");

        String url = "http://" + hu + ":8000/WMS/update_profile/";

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

                                Intent i=new Intent(getApplicationContext(),view_profile.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

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

                params.put("fname", fname);
                params.put("lname", lname);
                params.put("dob", dob);
                params.put("email", email);
                params.put("phon", phone);

                params.put("gender", gender);
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
