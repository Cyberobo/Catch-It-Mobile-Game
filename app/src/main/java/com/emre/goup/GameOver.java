package com.emre.goup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    TextView totalscore;
    Button playagain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        totalscore=findViewById(R.id.skor);
        playagain=findViewById(R.id.returngame);


        int score=getIntent().getIntExtra("skor",0);
        totalscore.setText(String.valueOf(score));


        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameScreen.startControl=false;
                startActivity(new Intent(GameOver.this,GameScreen.class));
                finish();
            }
        });

    }


}