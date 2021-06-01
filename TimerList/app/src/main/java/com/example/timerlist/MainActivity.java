package com.example.timerlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //리사이클러 변수들
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView nowDoText;

    //타이머 변수들
    TextView timerText;
    Button stopStartButton;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    Double doTime = 0.0;
    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //타이머 ID 설정
        timer = new Timer();
        timerText = findViewById(R.id.timerTextView);
        stopStartButton = findViewById(R.id.stopBtn);

        //리사이클러 ID 설정
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] textSet = {"1분","하루 3번 3분 양치질","5분 책 읽기","하루 10분 걷기"};
        double[] timeSet = {60,180,300,600};
        int[] imgSet = {R.drawable.checkmark,
                R.drawable.todolist_icon,
                R.drawable.todolist_icon,
                R.drawable.checkmark};
        nowDoText = findViewById(R.id.nowDoText);


        //어댑터 할당, 어댑터는 기본 어댑터를 확장한 커스텀 어댑터를 사용할 것
        adapter = new MyAdapter(textSet,imgSet,timeSet,this);
        recyclerView.setAdapter(adapter);
    }


    //타이머 설정
    public void resetTapped(View view){
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("ResetTimer");
        resetAlert.setMessage("Reset?");
        resetAlert.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(timerTask != null){
                    timerTask.cancel();
                    setButtonUI("S",R.color.green);
                    time = doTime;
                    timerStarted = false;
                    timerText.setText(getTimerText(doTime));
                }
            }
        });
        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        resetAlert.show();
    }

    public void startStopTapped(View view){

        if(timerStarted == false){
            timerStarted = true;
            setButtonUI("S", R.color.red);

            startTimer();
        }else{
            timerStarted = false;
            setButtonUI("S", R.color.green);

            timerTask.cancel();
        }

    }


    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(time>0)
                            time--;

                        timerText.setText(getTimerText(time));
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);


    }

    private String getTimerText(Double t) {
        int rounded = (int)Math.round(t);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600) ;

        return formatTime(seconds, minutes, hours);

    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + ":" +
                String.format("%02d", minutes) + ":" +
                String.format("%02d", seconds);
    }

    private void setButtonUI(String start, int color) {
        stopStartButton.setText(start);
        stopStartButton.setTextColor(ContextCompat.getColor(this, color));
    }

}
