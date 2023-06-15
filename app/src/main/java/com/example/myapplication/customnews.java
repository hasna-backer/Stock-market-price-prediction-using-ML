package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class customnews extends BaseAdapter {
    String [] title,url1,date;
    Context context;


    public customnews(Context applicationContext, String[] title, String[] url1, String[] date) {
        this.title=title;
        this.url1=url1;
        this.date=date;
        this.context=applicationContext;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (convertView==null)
        {
            view=inflater.inflate(R.layout.customnews,null);

        }
        else {
            view=convertView;
        }
        TextView title1=view.findViewById(R.id.textView66);
        CardView card1=view.findViewById(R.id.cardd);
        TextView date1=view.findViewById(R.id.textView64);

        title1.setText(title[position]);
        card1.setTag(url1[position]);
        date1.setText(date[position]);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=v.getTag().toString();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return view;
    }
}
