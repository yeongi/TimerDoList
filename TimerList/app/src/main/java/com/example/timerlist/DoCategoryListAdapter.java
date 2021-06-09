package com.example.timerlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timerlist.data.List;
import com.example.timerlist.db.DBHelper;
import com.example.timerlist.fragment.FragMain;

import java.util.ArrayList;

public class DoCategoryListAdapter extends BaseAdapter {
    ArrayList<List> items;
    private DBHelper mDBHelper;
    private Context context;
    private String selectedCategory;
    private FragMain fragMain;
    ArrayList<List> todayDo;
    EditText editDo;
    EditText editTime;

    public DoCategoryListAdapter(String select,Context context,ArrayList<List> todayDo) {
        mDBHelper = new DBHelper(context);
        this.context = context;
        selectedCategory = select;
        items = mDBHelper.getCatrgoryDoList(select);
        this.todayDo = todayDo;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List item = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dolist_item,parent,false);
        }

        TextView doText = convertView.findViewById(R.id.categoryDoText);
        TextView timeText = convertView.findViewById(R.id.categoryDoTime);
        Button sendBtn = convertView.findViewById(R.id.categorySendButton);
        Button editBtn = convertView.findViewById(R.id.categoryEditButton);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     //FragMain..Doing.add(item);
                todayDo.add(item);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(editBtn.getContext());
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View edit = inflater.inflate(R.layout.edit_dialog,null);

                editDo = (EditText) edit.findViewById(R.id.editText);
                editTime = (EditText) edit.findViewById(R.id.editTime);

                builder.setTitle("Edit Text");
                builder.setView(edit);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 눌렀을때 실행되는 내용
                        mDBHelper.UpdateSimpleTodo(editDo.getText().toString(),Integer.
                                parseInt(editTime.getText().toString()),item.getCategory());

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //취소 눌렀을때 실행되는 내용
                    }
                });
                builder.show();

            }

        });

        doText.setText(item.getDoing());
        timeText.setText(""+item.getTime());


        return convertView;
    }
}
