package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_view_reply extends BaseAdapter {

    String[] user,complaint,reply,date;
    private Context context;

    public custom_view_reply(Context appcontext, String[] user, String[] complaint, String [] reply,String[] date)
    {
        this.context=appcontext;
        this.user=user;
        this.complaint=complaint;
        this.reply=reply;
        this.date=date;


    }
    @Override
    public int getCount() {
        return complaint.length;
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

            gridView=inflator.inflate(R.layout.custom_view_reply,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tvdate=(TextView)gridView.findViewById(R.id.textView63);
        TextView tvcomplaint=(TextView)gridView.findViewById(R.id.textView65);
        TextView tvreply=(TextView)gridView.findViewById(R.id.textView67);



        tvdate.setText(date[i]);
        tvcomplaint.setText(complaint[i]);
        tvreply.setText(reply[i]);

        return gridView;
    }

    private Context getApplicationContext() {
        return context;
    }
}

