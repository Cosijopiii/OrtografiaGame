package com.edu.unistmo.ortografiagame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameSinAnt extends AppCompatActivity {

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 300;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            // mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();


        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS);
        }

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //  mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    String [] sinPla;
    String[] sinonimos;
    String [] antPla;
    String[] antonimos;
    String [] palabra;
    String[] sifnificado;
    String [] acentos;
    String[] acetoType;

    int typeOFgame=0;
    Typeface fontdog;
    Typeface fontorange;
    public String typeGame;
    TextView lblType,lblquestion,lblnet;
    Button btna1,btna2,btna3,btnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_sin_ant);

        mVisible = true;

        mContentView = findViewById(R.id.fullscreen_content_game_ant_sin);

        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS);
        }

        sinPla= getResources().getStringArray(R.array.palabras_sinonimo);
        sinonimos=getResources().getStringArray(R.array.sinonimos);
        antPla= getResources().getStringArray(R.array.palabras_Antonimo);
        antonimos=getResources().getStringArray(R.array.antonimo);
        acentos=getResources().getStringArray(R.array.palabras_acentos);
        acetoType=getResources().getStringArray(R.array.type_acentos);
        palabra= getResources().getStringArray(R.array.palabras);
        sifnificado=getResources().getStringArray(R.array.Significado);

        typeGame =getIntent().getStringExtra("category");
        Log.d("final", "makefinal: "+typeGame);
        fontdog = Typeface.createFromAsset(getAssets(), "god.TTF");
        fontorange = Typeface.createFromAsset(getAssets(), "orange.ttf");

        lblType= (TextView) findViewById(R.id.lblTypeGame);
        lblquestion=(TextView) findViewById(R.id.lblQuestion);
        btna1=(Button) findViewById(R.id.btn1Answ);
        btna2=(Button) findViewById(R.id.btn2Answ);
        btna3=(Button) findViewById(R.id.btn3Answ);
        btnext= (Button) findViewById(R.id.btnext);
        lblnet= (TextView) findViewById(R.id.txtnext);
        configureFont();
        cofigureType(typeGame);
        configureListenersButtons();
        btnext.setEnabled(false);


    }

    private void configureListenersButtons() {
        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (typeOFgame) {
                    case 0:
                        makeNext(sinPla, sinonimos);
                        break;
                    case 1:
                        makeNext(antPla, antonimos);
                        break;
                    case 2:
                        makeNext(acentos, acetoType);
                        break;
                    case 3:
                        makeNext(sifnificado, palabra);
                        break;

                }


            }
        });

        btna1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               switch (typeOFgame) {
                   case 0:
                       isthabutton(btna1.getText().toString(), sinonimos);
                       break;
                   case 1:
                       isthabutton(btna1.getText().toString(), antonimos);
                       break;
                   case 2:
                       isthabutton(btna1.getText().toString(), acetoType);
                       break;
                   case 3:
                       isthabutton(btna1.getText().toString(), palabra);
                       break;

               }


           }
       });
        btna2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (typeOFgame) {
                    case 0:
                        isthabutton(btna2.getText().toString(), sinonimos);
                        break;
                    case 1:
                        isthabutton(btna2.getText().toString(), antonimos);
                        break;
                    case 2:
                        isthabutton(btna2.getText().toString(), acetoType);
                        break;
                    case 3:
                        isthabutton(btna2.getText().toString(), palabra);
                        break;

                }



            }
        });
        btna3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (typeOFgame) {
                    case 0:
                        isthabutton(btna3.getText().toString(), sinonimos);
                        break;
                    case 1:
                        isthabutton(btna3.getText().toString(), antonimos);
                        break;
                    case 2:
                        isthabutton(btna3.getText().toString(), acetoType);
                        break;
                    case 3:
                        isthabutton(btna3.getText().toString(), palabra);
                        break;

                }


            }
        });




    }

    void  cofigureType(String typeGame){
        switch (typeGame){
            case "SINÓNIMOS":
                lblType.setText(Categories.TYPEGAME[0]);
                doitSinonimos(sinPla,sinonimos);
                typeOFgame=0;
            break;
            case "ANTÓNIMOS":
                lblType.setText(Categories.TYPEGAME[1]);
                doitSinonimos(antPla, antonimos);
                typeOFgame=1;
                break;
            case "ACENTOS":

                lblType.setText(Categories.TYPEGAME[2]);
                doitSinonimos(acentos, acetoType);
                typeOFgame=2;
                break;
            case "ADIVINA LA PALABRA":
                lblType.setText(Categories.TYPEGAME[3]);
                doitSinonimos(sifnificado, palabra);
                typeOFgame=3;
                break;
        }


    }

    private void makeNext(String p[],String []x){

        if(counter==4){
            lblnet.setText("Puntuación");
        }


        if(counter<5) {


            doitSinonimos(p,x);
            enabledisablebuttos(true);




        }
        else{

            makefinal();

        }


    }

    private void makefinal() {

        Intent i=new Intent(this,FullscreenActivity.class);
        i.putExtra("Aciertos",countergoods);
        Log.d("final", "makefinal: "+countergoods);
        startActivity(i);
    }

    private boolean [] checks=new boolean[5];
    private int posicitonofGOAL=0;

    int counter=0;
    int countergoods=0;
    private void isthabutton(String text,String [] key){



                if(text.equals(key[posicitonofGOAL])){
                    makeAlert(R.drawable.ic_check_green_700_48dp);
                    enabledisablebuttos(false);
                    countergoods++;

                }else{
                    makeAlert(R.drawable.ic_close_red_900_48dp);
                    enabledisablebuttos(false);

                }
                counter++;



    }

    void enabledisablebuttos(boolean b){
        btna1.setEnabled(b);
        btna2.setEnabled(b);
        btna3.setEnabled(b);
        btnext.setEnabled(!b);


    }
    public void makeAlert(int id){



        new SuperActivityToast(this, Style.amber(), Style.TYPE_STANDARD)
                .setText("")
                .setDuration(Style.DURATION_SHORT)
                .setFrame(Style.FRAME_STANDARD)
                .setIconResource(id)
                .setGravity(Gravity.CENTER, 0, 0)
                .show();
    }

    private void doitAdivinaPal() {
    }

    private void doitAcentos() {

    }

    private void doitAntonimos() {

    }


    private void doitSinonimos(String px[],String x[]){
        int postarge=getrand(px.length-1);
        posicitonofGOAL=postarge;
        String target= px[postarge];
        String resolvetarget=x[postarge];
        int postargetButton=getrand(2);
        lblquestion.setText(target);
        switch (postargetButton){
            case 0:
                btna1.setText(resolvetarget);

                if (postarge>x.length-1)
                btna2.setText(x[1]);
                else btna2.setText(x[postarge+1]);

                if (postarge==0)
                    btna3.setText(x[x.length-1]);
                else btna3.setText(x[postarge-1]);

                break;
            case 1:
                btna2.setText(resolvetarget);

                if (postarge>x.length-1)
                    btna3.setText(x[1]);
                else btna3.setText(x[postarge+1]);

                if (postarge==0)
                    btna1.setText(x[x.length-1]);
                else btna1.setText(x[postarge-1]);
                break;
            case 2:
                btna3.setText(resolvetarget);

                if (postarge>x.length-1)
                    btna2.setText(x[1]);
                else btna2.setText(x[postarge+1]);

                if (postarge==0)
                    btna1.setText(x[x.length-1]);
                else btna1.setText(x[postarge-1]);
                break;
        }




    }

    private int getrand(int b){

       return ThreadLocalRandom.current().nextInt(0,b);

    }


    public void configureFont(){
        lblType.setTypeface(fontorange);
        lblquestion.setTypeface(fontorange);
        btna1.setTypeface(fontorange);
        btna2.setTypeface(fontorange);
        btna3.setTypeface(fontorange);
        btnext.setTypeface(fontorange);
        lblnet.setTypeface(fontorange);

    }


}
