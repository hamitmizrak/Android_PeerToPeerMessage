package com.hamitmizrak.peertopeermessage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class Chronometre extends AppCompatActivity {
    //Global Variable
    private Button chronometreStartId;
    private Button chronometrePauseId;
    private Button chronometreRestartId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);
        //start
        chronometreStartId = findViewById(R.id.chronometreStartId);
        chronometrePauseId = findViewById(R.id.chronometrePauseId);
        chronometreRestartId = findViewById(R.id.chronometreRestartId);

        Chronometer chronometer = findViewById(R.id.chronometreId);

        chronometreStartId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.start();
            }
        });

        chronometrePauseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
            }
        });


        chronometreRestartId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        //end
    } //onCreate
} //End Class