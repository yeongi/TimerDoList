package com.example.timerlist.data;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.widget.ImageButton;
import android.widget.TextView;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

import org.w3c.dom.Text;

@SuppressLint("ParcelCreator")
public class MyCategory implements AsymmetricItem {
    private String title;

    public MyCategory(String title){
        this.title = title;
    }

    @Override
    public int getColumnSpan() {
        return 1;
    }

    @Override
    public int getRowSpan() {
        return 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flags);
    }

    public String getCategoryTitle() {
        return title;
    }
}
