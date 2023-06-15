package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class custom_view_companies extends BaseAdapter {


        String[] companyname,key;
        private Context context;

        public custom_view_companies(Context appcontext, String[] companyname, String[] key)
        {
            this.context=appcontext;
            this.companyname=companyname;
            this.key=key;
        }

        @Override
        public int getCount() {
            return key.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;
            if(view==null)
            {
                gridView=new View(context);
                //gridView=inflator.inflate(R.layout.customview, null);
                gridView=inflator.inflate(R.layout.custom_companies,null);

            }
            else
            {
                gridView=(View)view;

            }
            TextView tvcompanyname=(TextView)gridView.findViewById(R.id.textView10);
            TextView tvkey=(TextView)gridView.findViewById(R.id.textView19);

            tvcompanyname.setText(companyname[i]);
            tvkey.setText(key[i]);


            Button btstockinfo=(Button)gridView.findViewById(R.id.button5);
            Button btprediction=(Button)gridView.findViewById(R.id.button6);
            Button btbasicinfo=(Button)gridView.findViewById(R.id.button7);
            ImageView btaddbookmark=(ImageView)gridView.findViewById(R.id.button9);
            Button btcomparison=(Button)gridView.findViewById(R.id.button10);



            btcomparison.setTag(key[i]);

            btcomparison.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("companies", view.getTag().toString());
                    ed.commit();
                    Intent it = new Intent(getApplicationContext(), compare_graph.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);




                }
            });




            btaddbookmark.setTag(companyname[i]);
            btaddbookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String hu = sh.getString("ip", "");

                    String url = "http://" + hu + ":8000/WMS/addtobookmark/";

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

                                            Toast.makeText(getApplicationContext(),"Bookmark added successfully",Toast.LENGTH_LONG).show();

                                            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(i);


                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Failed to add bookmark", Toast.LENGTH_LONG).show();
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

                            params.put("companies", view.getTag().toString());
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
            });



            btstockinfo.setTag(key[i]);
            btstockinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("companies",view.getTag().toString());
                    ed.commit();

                    Intent ins= new Intent(context,stocks.class);
                    ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(ins);

                }
            });


            btprediction.setTag(key[i]);
            btprediction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("companies",view.getTag().toString());
                    ed.commit();

                    Intent ins= new Intent(context,prediction.class);
                    ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(ins);



                }
            });

            btbasicinfo.setTag(key[i]);
            btbasicinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("companies",view.getTag().toString());
                    ed.commit();

                    Intent ins= new Intent(context,basicinfo.class);
                    ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(ins);


                }
            });






            return gridView;
        }

    private Context getApplicationContext() {

            return context;
    }
}

