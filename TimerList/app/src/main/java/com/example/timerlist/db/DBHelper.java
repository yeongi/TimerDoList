package com.example.timerlist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.timerlist.data.List;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper

{
    private  static final int DB_VERSION = 1;
    private static final String DB_NAME = "List.db";
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT NOT NULL, " +
                "time INTEGER NOT NULL, " +
                "important INTEGER NOT NULL, " +
                "writeDate TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    //목록 조회
    public ArrayList<List> getTodoList(){
        ArrayList<List> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDate DESC", null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int time = cursor.getInt(cursor.getColumnIndex("time"));
                int important = cursor.getInt(cursor.getColumnIndex("important"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                List todoItem = new List(id,content,time,important,writeDate);
                todoItems.add(todoItem);
            }
        }
        cursor.close();

        return  todoItems;
    }
    
    public List getLastList(){
        List todoItem = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList WHERE MAX(id)", null);
        if(cursor.getCount() !=0){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            int time = cursor.getInt(cursor.getColumnIndex("time"));
            int important = cursor.getInt(cursor.getColumnIndex("important"));
            String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

             todoItem = new List(id,content,time,important,writeDate);
        }
        return todoItem;
    }

    // 목록 추가
    public void InsertTodo(String _content, int _time , int _important, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (content, time ,important, writeDate) " +
                "VALUES('" + _content + "','" + _time +"','" + _important + "' ,'" + _writeDate + "' );");
    }

    // 목록 수정
    public void UpdateTodo(String _content, int _time, int _important,String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET content='" + _content + "',time = '" + _time + ",' important = '"+_important+"'" +
                ", WHERE writeDate='" + _beforeDate + "'");
    }

    // 목록 삭제
    public void deleteTodo(String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate = '" +_beforeDate + "'");
    }
}
