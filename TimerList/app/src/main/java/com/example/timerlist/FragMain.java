package com.example.timerlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
enum Mode {
    TIMER_MODE ,STOP_WATCH
};


public class FragMain extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ViewGroup rootview;
    Context ct;
    SwipeRefreshLayout mySwipeRefreshLayout;

    public static FragMain newInstance(){
        FragMain FragMain = new FragMain();
        return FragMain;
    }

    //리사이클러 변수들
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView nowDoText;

    //타이머 변수들
    //모드 설정 기본값은 타이머
    Mode CurrentMode = Mode.TIMER_MODE;
    TextView timerText;
    TextView modeText;
    Button stopStartButton;
    Button resetButton;
    Button modeButton;
    Button saveButton;
    Button deleteButton;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    Double doTime = 0.0;
    boolean timerStarted = false;

    //할 일 변수
    ArrayList<List> Doing = new ArrayList<List>();
    String[] doingSet;
    double[] timeSet;
    int[] imgSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = (ViewGroup) inflater.inflate(R.layout.frag_main,container,false);
        //fragment의 context 가져 오기
        ct = rootview.getContext();

        //타이머 ID 설정
        timer = new Timer();
        timerText =  rootview.findViewById(R.id.timerTextView);
        stopStartButton =  rootview.findViewById(R.id.stopBtn);
        stopStartButton.setOnClickListener(this::startStopTapped);
        resetButton =  rootview.findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this::resetTapped);
        modeButton = rootview.findViewById(R.id.modeChangeButton);
        modeButton.setOnClickListener(this::modeChange);
        modeText = rootview.findViewById(R.id.modeText);

        //저장버튼
        saveButton = rootview.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this::saveDoing);

        //삭제버튼
        deleteButton = rootview.findViewById(R.id.deleteButton);

        //리사이클러 ID 설정
        recyclerView =  rootview.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(ct);
        recyclerView.setLayoutManager(layoutManager);

        nowDoText = rootview.findViewById(R.id.nowDoText);


        //어댑터 할당, 어댑터는 기본 어댑터를 확장한 커스텀 어댑터를 사용할 것
        adapter = new MyRecyclerViewAdapter(Doing,FragMain.this,ct);
        recyclerView.setAdapter(adapter);

        //리프레쉬 뷰
        mySwipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipe_layout);
        mySwipeRefreshLayout.setOnRefreshListener(this);


        return  rootview;
    }


    //리프레쉬 뷰 할 일
    @Override
    public void onRefresh() {
        //새로고침

        //새로고침 완료
        mySwipeRefreshLayout.setRefreshing(false);
    }

    //타이머 설정
    public void modeChange(View view){

        //실행 중엔 변경 불가
        if(timerStarted){
            Toast.makeText(ct, "타이머가 실행 중 입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        change();

    }

    public void resetTapped(View view){
        if(CurrentMode == Mode.TIMER_MODE) {
            AlertDialog.Builder resetAlert = new AlertDialog.Builder(ct);
            resetAlert.setTitle("ResetTimer");
            resetAlert.setMessage("타이머를 초기화 시키겠습니까?");
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
        if(CurrentMode == Mode.STOP_WATCH){
            AlertDialog.Builder resetAlert = new AlertDialog.Builder(ct);
            resetAlert.setTitle("Reset");
            resetAlert.setMessage("스탑워치를 초기화 시키겠습니까?");
            resetAlert.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(timerTask != null){
                        timerTask.cancel();
                        setButtonUI("S",R.color.green);
                        time = 0.0;
                        timerStarted = false;
                        timerText.setText(getTimerText(0.0));
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

    }

    public void startStopTapped(View view){

        if(timerStarted == false){
            if(CurrentMode == Mode.TIMER_MODE && time == 0.0){
                Toast.makeText(ct, "할 일을 고르세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            timerStarted = true;
            setButtonUI("S", R.color.red);

            startTimer();
        }else{
            timerStarted = false;
            setButtonUI("S", R.color.green);

            timerTask.cancel();
        }

    }


    //save 버튼 눌렀을 때
    private void saveDoing(View view) {
        //창을 하나 띄어서
        // 이밑에 인공지능 100 이미지 중요도 매개변수에 각각 넣으면 됨
        //
        Doing.add(new List("인공지능",100,R.drawable.checkmark,2));
        Doing.add(new List("정주형",5000,R.drawable.checkmark,500));
    }



    private void startTimer() {
        if(CurrentMode == Mode.TIMER_MODE){
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(time>0)
                        time--;

                    timerText.setText(getTimerText(time));
                }
            };
            timer.scheduleAtFixedRate(timerTask,0,1000);
        }

        if(CurrentMode == Mode.STOP_WATCH){
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                    timerText.setText(getTimerText(time));
                }
            };
            timer.scheduleAtFixedRate(timerTask,0,1000);
        }


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
        stopStartButton.setTextColor(ContextCompat.getColor(ct, color));
    }

    private void change(){
        if (CurrentMode == Mode.TIMER_MODE) {

            CurrentMode = Mode.STOP_WATCH;
            modeText.setText("STOPWATCH");
            nowDoText.setText("시간 잴 일");
            timerText.setText(getTimerText(0.0));
            time = 0.0;
            saveButton.setVisibility(View.VISIBLE);
            return;
        }

        if (CurrentMode == Mode.STOP_WATCH) {

            CurrentMode = Mode.TIMER_MODE;
            modeText.setText("TIMER");
            nowDoText.setText("DO");
            timerText.setText("시간");
            time = 0.0;
            saveButton.setVisibility(View.INVISIBLE);

            return;
        }
    }


}
