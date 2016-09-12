package com.example.johnny.suburban;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    TextView source;
    TextView title;
    TextView destination,tv;
    CheckBox checkBox;
    Button goButton;
    ListView sweetList,searchList;
    TextView layer2Title;
    ImageButton collapseButton,swapButton;
    RelativeLayout layer1,shade;
    LinearLayout layer2,layer3,sourceHolder,destinationHolder,expandButton;
    RelativeLayout topContainer;
    int layerPoss=0,sourceORdest=0;
    Animation anim1,anim2;
    SearchView sv;
    getStations stations;
    TextClock clock;
    String INPUT1_SOURCE;
    String INPUT2_DESTINATION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);





        this.source= (TextView) findViewById(R.id.source);
        this.title= (TextView) findViewById(R.id.titleText);
        this.destination= (TextView) findViewById(R.id.destination);
        this.clock=(TextClock)findViewById(R.id.textClock);
        this.checkBox= (CheckBox) findViewById(R.id.checkBox);
        this.goButton=(Button)findViewById(R.id.searchButton);
        this.layer2Title=(TextView)findViewById(R.id.layer2Title);
        TextView textview=(TextView)findViewById(R.id.textView);
        this.expandButton=(LinearLayout)findViewById(R.id.expand);
        this.layer1=(RelativeLayout)findViewById(R.id.layer1);
        this.layer2=(LinearLayout)findViewById(R.id.layer2);
        this.collapseButton=(ImageButton)findViewById(R.id.layer2Img);
        this.topContainer=(RelativeLayout)findViewById(R.id.topContainer);
        this.sourceHolder=(LinearLayout)findViewById(R.id.sourceHolder);
        this.destinationHolder=(LinearLayout)findViewById(R.id.destinationHolder);
        this.swapButton=(ImageButton)findViewById(R.id.swapButton);
        this.sv=(SearchView)findViewById(R.id.sv);
        this.layer3=(LinearLayout)findViewById(R.id.layer3);
        this.sweetList=(ListView)findViewById(R.id.layer2List);
        this.searchList=(ListView)findViewById(R.id.searchList);
        this.clock=(TextClock)findViewById(R.id.textClock);
        this.checkBox=(CheckBox)findViewById(R.id.checkBox);
        this.shade=(RelativeLayout)findViewById(R.id.shade);
        this.anim1= AnimationUtils.loadAnimation(this, R.anim.layout_trans_bottom_to_top);
        this.anim2= AnimationUtils.loadAnimation(this, R.anim.layout_trans_top_to_bottom);

        Typeface regularFont=Typeface.createFromAsset(getAssets(),"fonts/BebasNeue Regular.ttf");
        Typeface thinFont=Typeface.createFromAsset(getAssets(),"fonts/BebasNeue Light.ttf");
        Typeface bookFont=Typeface.createFromAsset(getAssets(),"fonts/BebasNeue Book.ttf");

        source.setTypeface(regularFont);
        destination.setTypeface(regularFont);
        clock.setTypeface(bookFont);
        checkBox.setTypeface(bookFont);
        goButton.setTypeface(regularFont);
        title.setTypeface(thinFont);
        textview.setTypeface(regularFont);
        layer2Title.setTypeface(regularFont);
        int id=sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        this.tv=(TextView)findViewById(id);
        tv.setTypeface(regularFont);
        int searchPlateId = sv.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlateView = sv.findViewById(searchPlateId);
        if (searchPlateView != null) {
            searchPlateView.setBackgroundColor(Color.TRANSPARENT);
        }






        String[] trains={"Howrah-Burdwan","Howrah-Bandel","Ultadanga-Dankuni","Sealdah-Barasat","Sealdah-Barackpore","Howrah-Belmuri","Howrah-Haripal"};
        ListAdapter sweetAdapter= new RecentSearches(this,trains,bookFont);
        sweetList.setClickable(true);
        sweetList.setAdapter(sweetAdapter);

        /*String[] stations={"Howrah (HWH)","Burdwan (BWN)","Bally (BLY)","Bandel (BNL)","Bidhan-Nagar (BNXR)","Dankuni (DKAE)","Sealdah (SDH)","Barasat (BB)","Barackpore (BCK)","Belur (BLR)","Belmuri (BMAE)","Liluah (LLU)","Haripal (HPL)"};
        ListAdapter sa=new StationSearch(this,stations,boldFont);
        searchList.setClickable(true);
        searchList.setDivider(null);
        searchList.setAdapter(sa);*/


        //_______DATABASE CODES__________

        this.stations=new getStations(this);
        final String[] stationNames=stations.names();

        final ArrayList<String> stationList=new ArrayList<>(Arrays.asList(stationNames));
        final StationSearch sa=new StationSearch(this,stationList);
        searchList.setClickable(true);
        searchList.setDivider(null);
        searchList.setAdapter(sa);

        Log.d("tag", "TEST ________________");


        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchList.smoothScrollToPosition(0);
                sa.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();
        shade.setVisibility(View.GONE);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(INPUT1_SOURCE!=null && INPUT2_DESTINATION!=null && !INPUT1_SOURCE.equals(INPUT2_DESTINATION)){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(sourceHolder);
                        TransitionManager.beginDelayedTransition(destinationHolder);
                    }
                    String temp1=destination.getText().toString();
                    destination.setText(source.getText().toString());
                    source.setText(temp1);
                    String temp=INPUT2_DESTINATION;
                    INPUT2_DESTINATION=INPUT1_SOURCE;
                    INPUT1_SOURCE=temp;
                }
            }
        });


        expandButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                layer2.startAnimation(anim1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(layer1);
                }
                layer1.setVisibility(View.GONE);
                layer2.setVisibility(View.VISIBLE);
                expandButton.setVisibility(View.GONE);
                layerPoss=1;

            }
        });
        collapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layer2.startAnimation(anim2);


                layer2.setVisibility(View.GONE);
                layer1.setVisibility(View.VISIBLE);
                expandButton.setVisibility(View.VISIBLE);
                layerPoss=0;

            }
        });


        ImageView imageView2=(ImageView)findViewById(R.id.imageView2);
        ImageView imageView3=(ImageView)findViewById(R.id.imageView3);
        changeTextColor(sourceHolder, source, imageView2, 1);
        changeTextColor(destinationHolder, destination, imageView3, 2);


        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView) view.findViewById(R.id.stationName)).getText().toString();
                String[] names = stations.names();
                String[] codes= stations.codes();

                if (sourceORdest == 1) {
                    source.setText(text);
                    for (int i = 0; i < names.length; i++) {
                        if (names[i] == text) {
                            INPUT1_SOURCE = codes[i];
                        }
                    }
                } else if (sourceORdest == 2) {
                    destination.setText(text);
                    for (int i = 0; i < names.length; i++) {
                        if (names[i] == text) {
                            INPUT2_DESTINATION = codes[i];
                        }
                    }
                }
                layer1.setVisibility(View.VISIBLE);
                layer3.setVisibility(View.GONE);
                expandButton.setVisibility(View.VISIBLE);
                tv.setText("");
                layerPoss = 0;
            }


        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(INPUT1_SOURCE!=null && INPUT2_DESTINATION!=null && INPUT1_SOURCE!=INPUT2_DESTINATION) {

                    shade.setVisibility(View.VISIBLE);

                    int count=0;
                    if(checkBox.isChecked()) {
                        count=1;
                    }
                    final int finalCount = count;
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getBaseContext(), ResultsActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("SOURCE", INPUT1_SOURCE);
                            i.putExtra("DESTINATION", INPUT2_DESTINATION);
                            Calendar cal = Calendar.getInstance();
                            Date currentLocalTime = cal.getTime();
                            DateFormat date = new SimpleDateFormat("hh:mm aa");
                            String localTime = date.format(currentLocalTime);
                            i.putExtra("TIMEC", localTime);
                            i.putExtra("count", finalCount);

                            getBaseContext().startActivity(i);
                        }
                    });
                    thread.start();

                }
                else{
                    Toast.makeText(getBaseContext(),"Enter valid details",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(layerPoss==1) {
                layer2.startAnimation(anim2);
                layer2.setVisibility(View.GONE);
                layer1.setVisibility(View.VISIBLE);
                expandButton.setVisibility(View.VISIBLE);
                layerPoss = 0;
                return true;

            }
            else if(layerPoss==2){
                //layer3.startAnimation(anim1);
                //TransitionManager.beginDelayedTransition(layer3);
                //TransitionManager.beginDelayedTransition(layer1);
                layer1.setVisibility(View.VISIBLE);
                layer3.setVisibility(View.GONE);
                expandButton.setVisibility(View.VISIBLE);
                layerPoss=0;
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }



    public void changeTextColor(final LinearLayout holder, final TextView text,final ImageView image,final int sourceORdest){

        holder.setOnTouchListener(new View.OnTouchListener() {
            final ColorStateList orgColor = source.getTextColors();

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                MainActivity.this.layerPoss=2;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        text.setTextColor(Color.WHITE);
                        holder.setBackgroundColor(Color.parseColor("#309229"));
                        if (sourceORdest == 1) {
                            image.setImageResource(R.drawable.ic_store_mall_directory_24dp_white);
                            layerPoss=2;
                            MainActivity.this.sourceORdest=sourceORdest;

                        } else {
                            image.setImageResource(R.drawable.ic_place_24dp_white);
                            layerPoss=2;
                            MainActivity.this.sourceORdest=sourceORdest;
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        text.setTextColor(orgColor);
                        holder.setBackgroundColor(Color.TRANSPARENT);
                        if (sourceORdest == 1){
                            image.setImageResource(R.drawable.ic_store_mall_directory_24dp);
                            callSearch(sourceORdest);
                        }
                        else {
                            image.setImageResource(R.drawable.ic_place_24dp);
                            callSearch(sourceORdest);
                        }

                        break;
                    }


                }


                return true;
            }
        });
    }

    public void callSearch(int c){
        //layer3.startAnimation(anim2);
        //TransitionManager.beginDelayedTransition(layer3);
        //TransitionManager.beginDelayedTransition(layer1);
        layer1.setVisibility(View.GONE);
        layer3.setVisibility(View.VISIBLE);
        expandButton.setVisibility(View.GONE);
        //sv.requestFocus();
        tv.requestFocus();
        String hint;
        if(c==1){
            hint="Search Source..";
        }
        else{
            hint="Search Destination..";
        }
        sv.setQueryHint(hint);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }



    /**
     * RelativeLayout.LayoutParams positionRule=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
     positionRule.addRule(RelativeLayout.BELOW,R.id.searchHolder);
     info.setLayoutParams(positionRule);
     */
}

