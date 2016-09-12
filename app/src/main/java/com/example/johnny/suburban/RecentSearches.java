package com.example.johnny.suburban;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Johnny on 1/25/2016.
 */
public class RecentSearches extends ArrayAdapter<String> {

    Typeface font;

    public RecentSearches(Context context, String[] trains,Typeface font) {
        super(context,R.layout.single_list, trains);
        this.font=font;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater kushInf=LayoutInflater.from(getContext());
        View customView=kushInf.inflate(R.layout.single_list, parent, false);

        String singleTrain=getItem(position);
        TextView content=(TextView)customView.findViewById(R.id.trainInfo);
        content.setText(singleTrain);

        content.setTypeface(font);

        return customView;
    }


}
