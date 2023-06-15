package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class custom_view_notification extends BaseAdapter {


        String[] date, title,content;
        private Context context;

        public custom_view_notification(Context appcontext, String[] date, String[] title, String [] content)
        {
            this.context=appcontext;
            this.date=date;
            this.title=title;
            this.content=content;
        }
        @Override
        public int getCount() {
            return date.length;
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

                gridView=inflator.inflate(R.layout.custom_notification,null);

            }
            else
            {
                gridView=(View)view;

            }
            TextView tvdate=(TextView)gridView.findViewById(R.id.textView29);
            TextView tvtitle=(TextView)gridView.findViewById(R.id.textView30);
            TextView tvdescription=(TextView)gridView.findViewById(R.id.textView31);



            tvdate.setText(date[i]);
            tvtitle.setText(title[i]);
            tvdescription.setText(content[i]);

            return gridView;
        }

    private Context getApplicationContext() {
            return context;
    }
}

