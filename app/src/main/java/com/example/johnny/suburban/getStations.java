package com.example.johnny.suburban;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Johnny 28 on 18-03-2016.
 */
public class getStations {

    private int c;
    private String str;
    Context myContext;
    private BufferedReader br = null;
    private StringBuffer sb;
    private String stationcode[] = new String[1825]; // this is the array for station code
    private String stationName[] = new String[1825]; // this is the station name
    //autocomplete with station name then get the index with which its matches...give the stationcode as input for same index ;)



    public getStations(Context c){
        this.myContext=c;

        try{
            br = new BufferedReader(
                    new InputStreamReader(myContext.getAssets().open("station.dat")));
            sb = new StringBuffer();

            while((str = br.readLine())!= null){
                sb.append(str);
            }
            String stationfile  = sb.toString();
            String stationarr[] = stationfile.split(";");
            stationcode = new String[stationarr.length/2] ;
            stationName = new String[stationarr.length/2];
            int j = 0;

            for(int i =0;i<stationarr.length;) {
                stationcode[j] = stationarr[i];
                i++;
                stationName[j] = stationarr[i];
                j++;
                i++;
            }

        }catch(Exception e){
            Log.d("tag", "UNABLE TO FETCH STATION NAMES");
        }

    }

    //user array for source and dest
    public String[] codes(){
        return stationcode;
    }
    public String[] names(){
        return stationName;
    }


}
