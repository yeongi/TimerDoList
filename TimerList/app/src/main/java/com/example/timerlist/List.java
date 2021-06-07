package com.example.timerlist;

public class List {
    String doing;
    double time;
    int image;
    int important;


    public List(String doing, double time, int image, int important){
        this.doing = doing;
        this.time = time;
        this.image = image;
        this.important = important;
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
}
