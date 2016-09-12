package com.example.johnny.suburban;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Johnny 28 on 21-03-2016.
 */
public class detailTrainAdapter extends ArrayAdapter<String> {

    Typeface font;
    String source,dest;


    public detailTrainAdapter(Context context, ArrayList<String> resource, String source, String dest) {
        super(context, R.layout.single_detail_train,resource);
        this.font= Typeface.createFromAsset(context.getAssets(), "fonts/BebasNeue Regular.ttf");
        this.source=source;
        this.dest=dest;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater kushInf=LayoutInflater.from(getContext());
        View customView=kushInf.inflate(R.layout.single_detail_train, parent, false);

        TextView time=(TextView)customView.findViewById(R.id.trainTime);
        TextView trainName=(TextView)customView.findViewById(R.id.trainName);
        time.setTypeface(font);
        trainName.setTypeface(font);

        String arr[]=getItem(position).split(";");


        trainName.setText(arr[0]);
        time.setText(arr[1]);
        if(arr[0].contains("("+source+")")||arr[0].contains("("+dest+")")) {
            trainName.setTextColor(Color.parseColor("#3F51B5"));
            time.setTextColor(Color.parseColor("#3F51B5"));
        }
        return customView;
    }
}

