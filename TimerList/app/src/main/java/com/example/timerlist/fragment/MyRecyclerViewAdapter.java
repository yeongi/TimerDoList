package com.example.timerlist.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timerlist.DONE;
import com.example.timerlist.R;
import com.example.timerlist.data.List;
import com.example.timerlist.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class  MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    // 이 데이터들을 가지고 각 뷰 홀더에 들어갈 텍스트 뷰에 연결할 것
    ArrayList<List> items;
    private DBHelper mDBHelper;
    private FragMain fragMain;
    private Context context;


    public MyRecyclerViewAdapter(FragMain fragMain, Context context) {
        this.fragMain = fragMain;
        this.context = context;
        mDBHelper = new DBHelper(context);
        items = mDBHelper.getTodayDoList();
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
        private DBHelper mDBHelper;
        private ArrayList<List> items;


        public MyViewHolder(@NonNull View view, FragMain fragMain, Context context, DBHelper mDBHelper,ArrayList<List> items) {
            super(view);
            this.imageView = view.findViewById(R.id.iv_pic);
            this.textView = view.findViewById(R.id.tv_text);
            this.doText = view.findViewById(R.id.doText);
            this.sendButton = view.findViewById(R.id.SendButton);
            this.deleteButton = view.findViewById(R.id.deleteButton);
            this.fragMain = fragMain;
            this.context = context;
            this.mDBHelper = mDBHelper;
            this.items = items;
        }

        public void setItem(List item , DBHelper dbhelper,Context context){
            textView.setText(item.getDoing());
            doText.setText(item.getDoing());
            imageView.setImageResource(item.getImage());
            if(fragMain.CurrentMode == Mode.TIMER_MODE){
                sendButton.setText("DO");
                sendButton.setVisibility(View.VISIBLE);
            }
            else
                sendButton.setVisibility(View.INVISIBLE);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(fragMain.CurrentMode == Mode.TIMER_MODE){

                        String temp = (doText.getText()).toString();
                        if(fragMain.timerStarted){
                            Toast.makeText(fragMain.getContext(), "타이머가 실행 중 입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fragMain.nowDoText.setText(temp);
                        fragMain.timerText.setText("");
                        fragMain.time = item.getTime();
                        fragMain.doTime = item.getTime();
                        fragMain.nowDate = item.getWriteDate();

                        return;

                    }else{

                    }
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //삭제 구현
                    if(fragMain.CurrentMode == Mode.TIMER_MODE){
                        deleteItem(item);
                        return;
                    }
                    if (fragMain.CurrentMode == Mode.STOP_WATCH){
                        //수정예정
                        //리스트에서 삭제
                        deleteItem(item);
                    }

                }
            });
        }

        private void deleteItem(List item) {
            if (fragMain.timerStarted) {
                Toast.makeText(fragMain.getContext(), "타이머가 실행 중 입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            //ToDoList 모드 일때 삭제 버튼
            //리스트에서 삭제
            try {
                if (items.size() > 0) {
                    mDBHelper.UpdateDoTodo(DONE.DEFAULT,item.getWriteDate());
                    Toast.makeText(fragMain.getContext(), "삭제 완료" +
                            "" +
                            "", Toast.LENGTH_SHORT).show();
                    items.remove(getAdapterPosition());
                    fragMain.onRefresh();
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            //어댑터에서 리사이클러 뷰에 반영
            return;
        }
    }

    // 어댑터 클래스 상속시 구현해야할 함수 3가지 : onCreateViewHolder, onBindViewHolder, getItemCount
    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰 홀더는 실제 레이아웃 파일과 매핑되어야하며,
    // extends의 Adater<>에서 <>안에들어가는 타입을 따른다.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(holderView,fragMain,context,mDBHelper,items);
        return myViewHolder;
    }

    // 실제 각 뷰 홀더에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        //데이터를 연결한다.
        List item = items.get(position);
        myViewHolder.setItem(item,mDBHelper,context);
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
