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

public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText et_lname;
    EditText et_fname;
    EditText et_dob;
    RadioButton male, female, others;
    EditText et_email;
    EditText et_pwd;
    EditText et_phone;
    EditText et_cpwd;
    Button bt_signup;

    final Calendar myCalendar = Calendar.getInstance();


    String gender = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_lname = (EditText) findViewById(R.id.editTextTextPersonName2);
        et_fname = (EditText) findViewById(R.id.editTextTextPersonName5);
        et_dob = (EditText) findViewById(R.id.editTextDate);
        male = findViewById(R.id.radioButton);
        female = findViewById(R.id.radioButton2);
        others = findViewById(R.id.radioButton3);
        et_email = (EditText) findViewById(R.id.editTextTextPersonName12);
        et_phone = (EditText) findViewById(R.id.editTextTextPersonName11);
        et_pwd = (EditText) findViewById(R.id.editTextTextPersonName3);
        et_cpwd = (EditText) findViewById(R.id.editTextTextPersonName10);
        bt_signup = (Button) findViewById(R.id.button4);
        bt_signup.setOnClickListener(this);

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
                new DatePickerDialog(signup.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
        final String pwd = et_pwd.getText().toString();
        final String phone = et_phone.getText().toString();
        final String cpwd = et_cpwd.getText().toString();


        if (fname.length() == 0) {
            et_fname.setError("Enter First Name");
            et_fname.requestFocus();

        }
        else if (!fname.matches("^[a-z,A-Z]*$")) {
            et_fname.setError("Enter a valid Name");
            et_fname.requestFocus();
        }
        else if (lname.length() == 0) {
            et_lname.setError("Enter Last Name");
            et_dob.requestFocus();

        }
        else if (!lname.matches("^[a-z,A-Z]*$")) {
            et_lname.setError("Enter a valid Name");
            et_lname.requestFocus();
        }
        else if (dob.length() == 0) {
            et_dob.setError("Missing");
            et_dob.requestFocus();
        }
        else if (dob.length() != 8) {
            et_dob.setError("Invalid dob");
            et_dob.requestFocus();
        }
        else if (email.length() == 0) {
            et_email.setError("Enter Email ");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter valid email");
            et_email.requestFocus();
        }
        else if (phone.length() == 0) {
            et_phone.setError("Enter Phone number");
        }
        else if (phone.length() != 10) {
            et_phone.setError("Invalid mobile");
            et_phone.requestFocus();
        }
        else if (pwd.length() == 0) {
            et_pwd.setError("Enter Password");
        }
        else if (!pwd.matches(("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$"))) {
            et_pwd.setError("Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character");
            et_pwd.requestFocus();
        }
        else if (cpwd.length() == 0) {
            et_cpwd.setError("Please confirm your password");
            et_pwd.requestFocus();

        }
        else if (!cpwd.equals(pwd)) {
            et_cpwd.setError("Password missmatch");
            et_pwd.requestFocus();

        }
        else {
            if (male.isChecked() == true) {
                gender = "male";
            }
            else if (female.isChecked() == true) {
                gender = "female";
            }
            else   {
                gender = "Others";
            }


                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":8000/WMS/user_registration_post/";

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

                                    } else if (jsonObj.getString("status").equalsIgnoreCase("exists")) {

                                        Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_LONG).show();

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
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("fname", fname);
                        params.put("lname", lname);
                        params.put("dob", dob);
                        params.put("email", email);
                        params.put("phon", phone);
                        params.put("password", pwd);
                        params.put("gender", gender);
//                    params.put("cpassword", cpwd);

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




