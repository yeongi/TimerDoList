package com.example.timerlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

    public DoCategoryListAdapter(String select,Context context) {
        mDBHelper = new DBHelper(context);
        this.context = context;
        selectedCategory = select;
        items = mDBHelper.getCatrgoryDoList(select);

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
            }
        });

        doText.setText(item.getDoing());
        timeText.setText(""+item.getTime());


        return convertView;
    }
}
