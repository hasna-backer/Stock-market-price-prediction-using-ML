package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_stocks extends BaseAdapter {


        String[] date,value,vol;
        private Context context;

        public custom_view_stocks(Context appcontext,String[] date,String[] value,String[] vol)
        {
            this.context=appcontext;
            this.date=date;
            this.value=value;
            this.vol=vol;




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
                //gridView=inflator.inflate(R.layout.customview, null);
                gridView=inflator.inflate(R.layout.custom_view_stocks,null);

            }
            else
            {
                gridView=(View)view;

            }
            TextView tvdate=(TextView)gridView.findViewById(R.id.textView11);
            TextView tvvalue=(TextView)gridView.findViewById(R.id.textView12);
            TextView tvvolume=(TextView)gridView.findViewById(R.id.textView18);

            tvdate.setText(date[i]);
            tvvalue.setText(value[i]);
            tvvolume.setText(vol[i]);





            return gridView;
        }
    }

