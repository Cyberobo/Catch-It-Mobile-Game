package com.emre.goup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity {

    ConstraintLayout cl;
    ImageView yellowPoint,pinkPoint,blackover,mainchracter;
    TextView score,startgametext;

    Timer timer=new Timer();
    Handler handler=new Handler();

    int mainchracterX;
    int mainchracterY;

    int blackoverX;
    int blackoverY;

    int yellowpointX;
    int yellowpointY;

    int pinkpointX;
    int pinkpointY;

    int mainchracterspeed;
    int yellowpointspeed;
    int blackoverpointspeed;
    int pinkpointspeed;

    int screenWidth;
    int screenHeight;

    int mainchracterWidth;
    int mainchracterHeight;

    int skor;

    boolean Touchcontrol=false;
    static boolean startControl=false;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        cl=findViewById(R.id.cl);
        yellowPoint=findViewById(R.id.yellowpoint);
        pinkPoint=findViewById(R.id.pinkpoint);
        blackover=findViewById(R.id.overpoint);
        mainchracter=findViewById(R.id.mainchracter);
        startgametext=findViewById(R.id.gamestartclick);
        score=findViewById(R.id.score);

        blackover.setX(-80);
        blackover.setY(-80);

        yellowPoint.setX(-80);
        yellowPoint.setY(-80);

        pinkPoint.setX(-80);
        pinkPoint.setY(-80);




        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(startControl){
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        Log.e("Motionevent","Touch the screen!");
                        Touchcontrol=true;
                    }

                    if(event.getAction()==MotionEvent.ACTION_UP){
                        Log.e("MotionEvent","Down the screen!");
                        Touchcontrol=false;
                    }
                }
                else{
                    startControl=true;
                    startgametext.setVisibility(View.INVISIBLE);

                    //get chracter position
                    mainchracterX=(int) mainchracter.getX();
                    mainchracterY=(int) mainchracter.getY();

                    //get chracter size
                    mainchracterWidth=mainchracter.getWidth();
                    mainchracterHeight=mainchracter.getHeight();

                    //get screen size
                    screenWidth=cl.getWidth();
                    screenHeight=cl.getHeight();


                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    chracterMov();
                                    itemMov();
                                    carpismacontrol();
                                }
                            });


                        }
                    },0,20);

                }





                return true;
            }
        });



    }


    public void chracterMov(){

        mainchracterspeed=Math.round(screenHeight/60);

        if(Touchcontrol){
            mainchracterY-=20;
        }
        else{
            mainchracterY+=20;
        }

        mainchracter.setY(mainchracterY);


        if(mainchracterY<=0){
            mainchracterY=0;
        }

        if(mainchracterY>=screenHeight-mainchracterHeight){
            mainchracterY=screenHeight-mainchracterHeight;
        }
    }


    public void itemMov(){

        yellowpointspeed=Math.round(screenWidth/60);
        blackoverpointspeed=Math.round(screenWidth/60);
        pinkpointspeed=Math.round(screenWidth/30);


        blackoverX-=blackoverpointspeed;

        if(blackoverX<0){
            blackoverX=screenWidth+20;
            blackoverY=(int) Math.floor(Math.random()*screenHeight);
        }

        yellowpointX-=yellowpointspeed;

        if(yellowpointX<0){
            yellowpointX=screenWidth+20;
            yellowpointY=(int) Math.floor(Math.random()*screenHeight);
        }

        pinkpointX-=pinkpointspeed;

        if(pinkpointX<0){
            pinkpointX=screenWidth+20;
            pinkpointY=(int) Math.floor(Math.random()*screenHeight);
        }

        blackover.setX(blackoverX);
        blackover.setY(blackoverY);

        yellowPoint.setX(yellowpointX);
        yellowPoint.setY(yellowpointY);

        pinkPoint.setX(pinkpointX);
        pinkPoint.setY(pinkpointY);
    }

    public void carpismacontrol(){

        int yellowdairemerkezX=yellowpointX+yellowPoint.getWidth()/2;
        int yellowdairemerkezY=yellowpointY+yellowPoint.getHeight()/2;

        if(0<=yellowdairemerkezX && yellowdairemerkezX<=mainchracterWidth && mainchracterY <= yellowdairemerkezY && yellowdairemerkezY <= mainchracterY+mainchracterHeight){
            skor+=20;
            yellowpointX=-10;

        }


        int blackdairemerkezX=blackoverX+blackover.getWidth()/2;
        int blackdairemerkezY=blackoverY+blackover.getHeight()/2;

        if(0<=blackdairemerkezX && blackdairemerkezX<=mainchracterWidth && mainchracterY <= blackdairemerkezY && blackdairemerkezY <= mainchracterY+mainchracterHeight){

            timer.cancel();
            timer=null;

            Intent intent=new Intent(GameScreen.this,GameOver.class);
            intent.putExtra("skor",skor);
            startActivity(intent);
            finish();

        }

        int pinkdairemerkezX=pinkpointX+pinkPoint.getWidth()/2;
        int pinkdairemerkezY=pinkpointY+pinkPoint.getHeight()/2;

        if(0<=pinkdairemerkezX && pinkdairemerkezX<=mainchracterWidth && mainchracterY <= pinkdairemerkezY && pinkdairemerkezY <= mainchracterY+mainchracterHeight){
            skor+=50;
            pinkpointX=-10;

        }


        score.setText(String.valueOf(skor));


    }

}