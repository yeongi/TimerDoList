package com.example.timerlist.data;

import com.example.timerlist.R;

import java.util.Date;

public class List {
    int id;
    String doing;
    double time;
    int image;
    int important;
    String writeDate;


    public List(int id , String doing, double time, int important, String date){
        this.id = id;
        this.doing = doing;
        this.time = time;
        this.image = R.drawable.checkmark;
        this.important = important;
        this.writeDate = date;
    }

    public String getDoing(){
        return doing;
    }

    public double getTime(){
        return time;
    }

    public int getImage(){
        return image;
    }

    public int getImportant(){
        return important;
    }

    public String getWriteDate() {return writeDate;}
}
