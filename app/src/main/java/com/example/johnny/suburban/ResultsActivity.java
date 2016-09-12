package com.example.johnny.suburban;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ResultsActivity extends AppCompatActivity {

    String source;
    String dest, clock;
    TextView tv1, tv2, tv3, tv4,noResText;
    ListView resultList,detailedList;
    int count;
    RelativeLayout noRes,shade;
    LinearLayout topView,detailedTrain;
    ProgressBar pg;
    Animation anim1,anim2;
    ArrayList<String> detailedArr=new ArrayList<>();
    int layerPoss=0;
    String wholefile;
    String arr[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.tv1 = (TextView) findViewById(R.id.textView3);
        this.tv2 = (TextView) findViewById(R.id.textView4);
        this.tv3 = (TextView) findViewById(R.id.textView5);
        this.tv4 = (TextView) findViewById(R.id.textView6);
        this.topView=(LinearLayout)findViewById(R.id.topView);
        this.pg=(ProgressBar)findViewById(R.id.progressBar);
        this.resultList = (ListView) findViewById(R.id.resultList);
        this.noResText=(TextView)findViewById(R.id.noResText);
        this.noRes = (RelativeLayout) findViewById(R.id.noRes);
        this.shade=(RelativeLayout)findViewById(R.id.shade2);
        this.detailedTrain=(LinearLayout)findViewById(R.id.detailedTrain);
        this.anim1= AnimationUtils.loadAnimation(this, R.anim.layout_trans_bottom_to_top);
        this.anim2= AnimationUtils.loadAnimation(this, R.anim.layout_trans_top_to_bottom);
        this.detailedList=(ListView)findViewById(R.id.detailedList);

        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue Regular.ttf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue Bold.ttf");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.source = extras.getString("SOURCE");
            this.dest = extras.getString("DESTINATION");
            this.clock = extras.getString("TIMEC").trim().toString();
            this.count = extras.getInt("count");

        }
        tv1.setText(source + " - " + dest);
        tv1.setTypeface(regularFont);
        tv2.setTypeface(regularFont);
        tv3.setTypeface(regularFont);
        tv4.setTypeface(regularFont);
        noResText.setTypeface(boldFont);

        try {
            show();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }




    @Override
    protected void onStart() {

        final detailTrainAdapter dta=new detailTrainAdapter(getBaseContext(),detailedArr,source,dest);
        detailedList.setDivider(null);
        detailedList.setAdapter(dta);

        super.onStart();
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shade.setVisibility(View.VISIBLE);
                detailedTrain.startAnimation(anim1);
                resultList.setEnabled(false);
                detailedTrain.setVisibility(View.VISIBLE);
                detailedArr.clear();

                String text = ((TextView) view.findViewById(R.id.trainInfo)).getText().toString();
                String temp[]=text.split("\\(");
                String temp1[]=temp[1].split("\\)");

                Log.d("tag", temp1[0]);
                for(String p:arr){
                    if(p.contains(temp1[0])){
                        Log.d("tag","FOUND <-");
                        String[] temp2=p.split("\\|");
                        for(int j=1;j<temp2.length-3;j=j+5) {

                            detailedArr.add(temp2[j] + ";" + temp2[j + 3]);

                        }
                    }
                }
                dta.notifyDataSetChanged();
                detailedList.smoothScrollToPosition(0);

                layerPoss=1;

            }
        });
        shade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList.setEnabled(true);
                detailedTrain.startAnimation(anim2);
                detailedTrain.setVisibility(View.GONE);
                shade.setVisibility(View.GONE);

                layerPoss=0;

            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if(layerPoss==1) {
                resultList.setEnabled(true);
                detailedTrain.startAnimation(anim2);
                detailedTrain.setVisibility(View.GONE);
                shade.setVisibility(View.GONE);

                layerPoss = 0;
                return true;

            }

        }
        return super.onKeyDown(keyCode, event);
    }


    public void show() throws Exception {

        int c;
        String str;
        String input1 = "(" + source + ")";
        String input2 = "(" + dest + ")";
        ArrayList<String> source = new ArrayList<String>();
        ArrayList<String> arrival = new ArrayList<String>();
        ArrayList<String> dept = new ArrayList<String>();
        String wholefiletrain;


        BufferedReader br = null;
        StringBuffer sb;

        String train_Arr[][];
        String trainNo[] = new String[2132];
        String trainName[] = new String[2132];// this is the station name
//autocomplete with station name then get the index with which its matches...give the stationcode as input for same index ;)
        String Trainname;
        String trainsource;
        String traindest;
        ArrayList<String> trainnameresult = new ArrayList<String>();

        ArrayList<String> trainnoresult = new ArrayList<String>();

        ArrayList<String> timeresult = new ArrayList<String>();
        ArrayList<String> finalresult = new ArrayList<String>();
        ArrayList<String> reachtime = new ArrayList<String>();
        String inputtime = "INVALID";
        if (count == 1) {
            inputtime = clock;
            Log.d("tag", "count=" + count);
        }
        Date outputtime = null;
        Log.d("tag", inputtime + " Running");


        DateFormat df = new SimpleDateFormat("hh:mm aa");
        DateFormat outputformat = new SimpleDateFormat("HH:mm");


        //trainname array

        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("train.dat")));
            sb = new StringBuffer();
            while ((str = br.readLine()) != null) {

                sb.append(str);


            }
            wholefiletrain = sb.toString();
            arr = wholefiletrain.split(",");


            int x = 0;

            for (int i = 0; i < arr.length; ) {
                trainNo[x] = arr[i];
                i++;
                trainName[x] = arr[i];
                i++;
                x++;
            }

        } catch (Exception e) {

        }


        //user array for source and dest




        try {long start1 = System.nanoTime();
            c = 0;
            wholefile = "";
            br = new BufferedReader(
                    new InputStreamReader(getAssets().open("myfile4.0.dat")));


            sb = new StringBuffer();

            while ((str = br.readLine()) != null) {

                sb.append(str);
                sb.append("\n");

            }
            wholefile = sb.toString();
            this.arr = wholefile.split(";");
            train_Arr = new String[arr.length][2500];
            for (int i = 0; i < arr.length; i++) {
                train_Arr[i] = arr[i].split("\\|");
                c++;

            }


            Trainname = "";
            for (int j = 0; j < arr.length; j++) {
                // System.out.println(train_Arr[j][0]);

                for (int i = 1; i < train_Arr[j].length; ) {
                    //System.out.println(train_Arr[j][i].trim());
                    source.add(train_Arr[j][i].trim());
                    arrival.add(train_Arr[j][i + 2].trim());

                    //System.out.println(train_Arr[j][i+2].trim());
                    try {
                        //System.out.println(train_Arr[j][i+3].trim());
                        dept.add(train_Arr[j][i + 3].trim());
                        i += 5;
                    } catch (Exception e) {
                        dept.add(train_Arr[j][i + 2].trim());
                        i += 5;
                    }

                }
                trainsource = source.get(0);
                traindest = source.get(source.size() - 1);
                Trainname = trainsource + "-" + traindest + " " + "local";
                //System.out.println(Trainname);

                for (int k = 0; k < source.size(); k++) {
                    if (source.get(k).contains(input1)) {
                        for (int l = k + 1; l < source.size(); l++) {
                            if (source.get(l).contains(input2)) {

                                if (arrival.get(k).contains("Source")) {

                                    outputtime = df.parse(dept.get(k));

                                } else {

                                    outputtime = df.parse(arrival.get(k));


                                }


                                if (inputtime == "INVALID" || outputtime.compareTo(df.parse(inputtime)) > 0) {


                                    //System.out.println(Trainname);
                                    //System.out.println(train_Arr[j][0]);
                                    //System.out.println(arrival.get(k));
                                    //System.out.println(dept.get(k));
                                    //result.append(Trainname.trim()+"("+train_Arr[j][0].trim()+")"+"\n"+"station"+input1+"\n"+"arrival-"+arrival.get(k)+"\n"+"dept-"+dept.get(k)+"\n");
                                    //resultarr.add("trainmae" + ";"+"(" + train_Arr[j][0].trim() + ")" +";" + arrival.get(k) + ";"  + dept.get(k) + ";");
                                    //System.out.println("exe");
                                    //t.setText(result);

                                    if (arrival.get(k).contains("Source")) {
                                        Date date = null;
                                        String output = null;
                                        date = df.parse(dept.get(k));
                                        output = outputformat.format(date);
                                        timeresult.add(output);


                                        // timeresult.add(dept.get(k));
                                    } else if (dept.get(k).contains("Destination")) {
                                        Date date = null;
                                        String output = null;
                                        date = df.parse(arrival.get(k));
                                        output = outputformat.format(date);
                                        timeresult.add(output);


                                        // timeresult.add(arrival.get(k));
                                    } else {
                                        Date date = null;
                                        String output = null;
                                        date = df.parse(arrival.get(k));
                                        output = outputformat.format(date);
                                        timeresult.add(output);


                                        //timeresult.add(arrival.get(k));
                                    }
                                    Date date = null;
                                    String output = null;
                                    date = df.parse(arrival.get(l));
                                    output = outputformat.format(date);
                                    reachtime.add(output);


                                    for (int i = 0; i < trainNo.length; i++) {
                                        if (train_Arr[j][0].trim().contains(trainNo[i].trim())) {
                                            trainnameresult.add(trainName[i]);
                                        }
                                    }


                                    trainnoresult.add(train_Arr[j][0].trim());

                                }


                            }
                        }
                    }
                }
                source.clear();
                arrival.clear();
                dept.clear();
            }



            //5th



            for (int i = 0; i < trainnameresult.size(); i++) {
                finalresult.add(timeresult.get(i) + "|" + trainnameresult.get(i) + "(" + trainnoresult.get(i) + ")" + "|" + reachtime.get(i));
            }



            Collections.sort(finalresult);//final result is the array list


            // ----------THE REAL DEAL--------------
            Log.d("tag", "Time taken-" + String.valueOf((System.nanoTime() - start1) / 1e9));
            Toast.makeText(getBaseContext(),String.valueOf((System.nanoTime() - start1) / 1e9), Toast.LENGTH_SHORT).show();

            Log.d("tag",finalresult.get(0));
            GenerateResults gr = new GenerateResults(this, finalresult);
            resultList.setClickable(true);
            resultList.setDivider(null);
            resultList.setAdapter(gr);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(noRes);
            }
            noRes.setVisibility(View.VISIBLE);
            if(finalresult.get(0)==null){
                noRes.setVisibility(View.GONE);
            }

        } catch (IOException e) {
            //log the exception
        }

    }



}
