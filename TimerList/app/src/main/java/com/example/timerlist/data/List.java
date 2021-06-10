package com.example.timerlist.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.timerlist.R;

import java.io.Serializable;
import java.util.Date;

public class List implements Serializable, Parcelable {
    int id;
    String doing;
    double time;
    int image;
    int important;
    String writeDate;
    String category;


    public List(int id , String doing, double time, int important, String date ,String category){
        this.id = id;
        this.doing = doing;
        this.time = time;
        this.image = R.drawable.checkmark;
        this.important = important;
        this.writeDate = date;
        this.category = category;
    }

    protected List(Parcel in) {
        id = in.readInt();
        doing = in.readString();
        time = in.readDouble();
        image = in.readInt();
        important = in.readInt();
        writeDate = in.readString();
        category = in.readString();
    }

    public static final Creator<List> CREATOR = new Creator<List>() {
        @Override
        public List createFromParcel(Parcel in) {
            return new List(in);
        }

        @Override
        public List[] newArray(int size) {
            return new List[size];
        }
    };

    public String getDoing(){
        return doing;
    }

    public double getTime(){
        return time;
    }

    public int getImage(){
        return image;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public int getImportant(){
        return important;
    }

    public String getWriteDate() {return writeDate;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flags);
    }
}
