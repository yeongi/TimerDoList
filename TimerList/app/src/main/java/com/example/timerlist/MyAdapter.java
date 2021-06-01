package com.example.timerlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // 이 데이터들을 가지고 각 뷰 홀더에 들어갈 텍스트 뷰에 연결할 것
    private String[] textSet;
    private int[] imgSet;
    private double[] timeSet;
    private MainActivity mainActivity;

    public MyAdapter(String[] textSet, int[] imgSet, double[] timeSet, MainActivity mainActivity) {
        this.textSet = textSet;
        this.imgSet = imgSet;
        this.timeSet = timeSet;
        this.mainActivity = mainActivity ;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //리사이클러 뷰에 들어갈 뷰 홀더 그리고 그 뷰 홀더에 들어갈 아이템들을 지정
        public ImageView imageView;
        public TextView textView;
        public TextView doText;
        public TextView nowDoText;
        public Button sendButton;
        private double[] timeSet;
        private String[] textSet;

        public MyViewHolder(@NonNull View view,double[] timeSet,String[] textSet) {
            super(view);
            this.imageView = view.findViewById(R.id.iv_pic);
            this.textView = view.findViewById(R.id.tv_text);
            this.doText = view.findViewById(R.id.doText);
            this.nowDoText = view.findViewById(R.id.nowDoText);
            this.sendButton = view.findViewById(R.id.SendButton);
            this.timeSet = timeSet;
            this.textSet = textSet;
        }

        private Double getTime(int position) {
            return timeSet[position];
        }

        private String getDoText(int position) {
            return textSet[position];
        }
    }

    // 어댑터 클래스 상속시 구현해야할 함수 3가지 : onCreateViewHolder, onBindViewHolder, getItemCount
    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰 홀더는 실제 레이아웃 파일과 매핑되어야하며,
    // extends의 Adater<>에서 <>안에들어가는 타입을 따른다.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(holderView,timeSet,textSet);
        return myViewHolder;
    }

    // 실제 각 뷰 홀더에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        //데이터를 연결한다.
        myViewHolder.textView.setText("DO");
        myViewHolder.doText.setText(this.textSet[position]);
        myViewHolder.imageView.setBackgroundResource(this.imgSet[position]);
        myViewHolder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = (myViewHolder.doText.getText()).toString();
                if(mainActivity.timerStarted){
                    Toast.makeText(mainActivity, "타이머가 실행 중 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mainActivity.nowDoText.setText(myViewHolder.getDoText(position));
                mainActivity.timerText.setText("");
                mainActivity.time = myViewHolder.getTime(position);
                mainActivity.doTime = myViewHolder.getTime(position);
            }
        });

    }

    //리사이클러뷰안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        return textSet.length > imgSet.length ? textSet.length : imgSet.length;
    }
}
