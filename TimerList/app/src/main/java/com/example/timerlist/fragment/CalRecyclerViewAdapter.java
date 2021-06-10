package com.example.timerlist.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timerlist.R;
import com.example.timerlist.data.List;
import com.example.timerlist.db.DBHelper;

import java.util.ArrayList;

public class CalRecyclerViewAdapter extends RecyclerView.Adapter<CalRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<List> dateList;

    public CalRecyclerViewAdapter(Context context,ArrayList<List> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CalDoingText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.CalDoingText = itemView.findViewById(R.id.calDoingText);
        }


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calander_view_holder,parent,false);
        CalRecyclerViewAdapter.ViewHolder myViewHolder = new CalRecyclerViewAdapter.ViewHolder(holderView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List item = dateList.get(position);
        String text = " 카테고리 :" + item.getCategory() + "\n" + item.getDoing() +" \n 일시 : "+item.getWriteDate() ;
        holder.CalDoingText.setText(text);
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }


}
