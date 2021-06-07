package com.example.timerlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    // 이 데이터들을 가지고 각 뷰 홀더에 들어갈 텍스트 뷰에 연결할 것
    ArrayList<List> items;
    private FragMain fragMain;
    private Context context;


    public MyRecyclerViewAdapter(ArrayList<List> items,FragMain fragMain, Context context) {
        this.items = items;
        this.fragMain = fragMain;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //리사이클러 뷰에 들어갈 뷰 홀더 그리고 그 뷰 홀더에 들어갈 아이템들을 지정
        public ImageView imageView;
        public TextView textView;
        public TextView doText;
        public Button sendButton;
        public Button deleteButton;
        private FragMain fragMain;
        private Context context;


        public MyViewHolder(@NonNull View view,FragMain fragMain,Context context) {
            super(view);
            this.imageView = view.findViewById(R.id.iv_pic);
            this.textView = view.findViewById(R.id.tv_text);
            this.doText = view.findViewById(R.id.doText);
            this.sendButton = view.findViewById(R.id.SendButton);
            this.deleteButton = view.findViewById(R.id.deleteButton);
            this.fragMain = fragMain;
            this.context = context;
        }

        public void setItem(List item){
            textView.setText(item.getDoing());
            doText.setText(item.getDoing());
            imageView.setImageResource(item.getImage());
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fragMain.CurrentMode == Mode.TIMER_MODE){
                        String temp = (doText.getText()).toString();
                        if(fragMain.timerStarted){
                            Toast.makeText(fragMain.getContext(), "타이머가 실행 중 입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fragMain.nowDoText.setText(doText.getText());
                        fragMain.timerText.setText("");
                        fragMain.time = item.getTime();
                        fragMain.doTime = item.getTime();
                        return;
                    }
                    if (fragMain.CurrentMode == Mode.STOP_WATCH){
                        Toast.makeText(fragMain.getContext(), "스탑 워치 리스트 구현 예정", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //삭제 구현
                    if(fragMain.CurrentMode == Mode.TIMER_MODE){
                        //ToDoList 모드 일때 삭제 버튼
                        //리스트에서 삭제
                        try{
                            if(fragMain.Doing.size() > 0 ){
                                fragMain.Doing.remove(getAdapterPosition());
                            }
                        }catch (ArrayIndexOutOfBoundsException e){

                        }
                        //어댑터에서 리사이클러 뷰에 반영
                        return;
                    }
                    if (fragMain.CurrentMode == Mode.STOP_WATCH){
                        //수정예정
                        Toast.makeText(fragMain.getContext(), "스탑 워치 리스트 구현 예정", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    // 어댑터 클래스 상속시 구현해야할 함수 3가지 : onCreateViewHolder, onBindViewHolder, getItemCount
    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰 홀더는 실제 레이아웃 파일과 매핑되어야하며,
    // extends의 Adater<>에서 <>안에들어가는 타입을 따른다.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(holderView,fragMain,context);
        return myViewHolder;
    }

    // 실제 각 뷰 홀더에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        //데이터를 연결한다.
        List item = items.get(position);
        myViewHolder.setItem(item);
        myViewHolder.textView.setText("DO");
    }

    //리사이클러뷰안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(List item){
        items.add(item);
    }
}
