package com.example.johnny.suburban;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Johnny 28 on 01-02-2016.
 */
public class StationSearch extends ArrayAdapter<String> implements Filterable {

    private Typeface font;
    private ArrayList<String> stations;
    private ArrayList<String> CopyStations;
    stationFilter stationFilter;


    public StationSearch(Context context, ArrayList<String> stations) {
        super(context,R.layout.single_search_item,stations);
        this.stations=stations;
        this.CopyStations=stations;
        this.font=Typeface.createFromAsset(context.getAssets(),"fonts/BebasNeue Regular.ttf");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater kushInf=LayoutInflater.from(getContext());
        View customView=kushInf.inflate(R.layout.single_search_item, parent, false);

        String singleStation=getItem(position);
        TextView content=(TextView)customView.findViewById(R.id.stationName);
        content.setText(singleStation);

        content.setTypeface(font);

        return customView;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public String getItem(int position) {
        return stations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (stationFilter == null) {
            stationFilter = new stationFilter();
        }

        return stationFilter;
    }

    private class stationFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();


            if (constraint == null || constraint.length() == 0) {

                results.values = CopyStations;
                results.count = CopyStations.size();
                Log.d("tag",String.valueOf(results.count));

            }
            else {

                ArrayList<String> nStationList = new ArrayList<String>();


                for (String p:CopyStations) {


                    if (p.toUpperCase().toString().startsWith(constraint.toString().toUpperCase()) || p.toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        nStationList.add(p);

                    }

                }


                results.values = nStationList;
                results.count = nStationList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {

            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                stations = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        }

    }




}
