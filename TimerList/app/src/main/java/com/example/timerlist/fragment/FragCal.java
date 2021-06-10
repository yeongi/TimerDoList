package com.example.timerlist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.timerlist.R;
import com.example.timerlist.db.DBHelper;

import java.util.Calendar;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class FragCal extends Fragment {
    private View view;
    DBHelper mDBHelper;

    public static FragCal newInstance(){
        FragCal fragCal = new FragCal();
        return fragCal;
    }

    RecyclerView dateList;
    CalRecyclerViewAdapter mAdapter;
    LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_cal,container,false);
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view , R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        mDBHelper = new DBHelper(view.getContext());
        dateList = view.findViewById(R.id.calendarRecyclerView);
        dateList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        dateList.setLayoutManager(layoutManager);

        mAdapter = new CalRecyclerViewAdapter(view.getContext(),mDBHelper.getTodoList());
        dateList.setAdapter(mAdapter);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                mAdapter = new CalRecyclerViewAdapter(view.getContext(),mDBHelper.getTodoList());
                dateList.setAdapter(mAdapter);
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });





        return view;
    }



}
