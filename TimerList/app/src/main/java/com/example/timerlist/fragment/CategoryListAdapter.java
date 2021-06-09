package com.example.timerlist.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.example.timerlist.DoCategoryListAdapter;
import com.example.timerlist.R;
import com.example.timerlist.data.List;
import com.example.timerlist.data.MyCategory;
import com.example.timerlist.fragment.FragCategory;

import java.util.ArrayList;

public class CategoryListAdapter extends BaseAdapter {
    ArrayList<MyCategory> items;
    Context context;
    FragCategory fragCategory;
    DoCategoryListAdapter myAdapter;

    CategoryListAdapter(ArrayList<MyCategory> items, Context context, FragCategory fragCategory) {
        this.items = items;
        this.context = context;
        this.fragCategory = fragCategory;
    }

    //position에 위치한 데이터를 화면에 출력하는데 사용할 View 를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyCategory item = items.get(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_button,parent,false);
        }
        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.categoryImageButton);
        TextView textview = (TextView)convertView.findViewById(R.id.categoryText);

        imageButton.setImageResource(R.drawable.todolist_icon);
        textview.setText(item.getCategoryTitle());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 눌렀을때 구현
                showAlertDialog();

            }

            private void showAlertDialog() {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dolist_dialog,null);

                final ListView listView = view.findViewById(R.id.categoryDoList);

                myAdapter = new DoCategoryListAdapter(item.getCategoryTitle(),view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(listView.getContext());
                builder.setTitle(item.getCategoryTitle());
                builder.setView(view);

                builder.setPositiveButton("확인",null);
                builder.setAdapter(myAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



                builder.show();

            }

        });


        //데이터 셋에서 참조 획득
        MyCategory myCategory = items.get(position);

        //데이터 반영
        textview.setText(myCategory.getCategoryTitle());
        return convertView;
    }

    public void addItem() {
        MyCategory myCategory = new MyCategory("임시");
        items.add(myCategory);
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

}


