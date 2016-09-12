package com.example.johnny.suburban;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Johnny 28 on 19-03-2016.
 */
public class GenerateResults extends ArrayAdapter<String> {

    ArrayList<String> finalResults;
    Typeface font,boldFont;
    public GenerateResults(Context context, ArrayList<String> finalResults) {
        super(context,R.layout.result_single_layout,finalResults);
        this.finalResults=finalResults;
        this.font=Typeface.createFromAsset(context.getAssets(),"fonts/BebasNeue Regular.ttf");
        this.boldFont=Typeface.createFromAsset(context.getAssets(),"fonts/BebasNeue Bold.ttf");

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater kushInf=LayoutInflater.from(getContext());
        View customView=kushInf.inflate(R.layout.result_single_layout, parent, false);

        String singleResult=getItem(position);
        String[] temp=singleResult.split("\\|");
        String arrTime=temp[0];
        String train=temp[1];
        String reachTime=temp[2];

        TextView arrT=(TextView)customView.findViewById(R.id.arrTime);
        TextView trainD=(TextView)customView.findViewById(R.id.trainInfo);
        TextView reachT=(TextView)customView.findViewById(R.id.reachTime);
        arrT.setText(arrTime);
        trainD.setText(train);
        reachT.setText(reachTime);

        arrT.setTypeface(boldFont);
        trainD.setTypeface(font);
        reachT.setTypeface(boldFont);

        return customView;
    }
}
