package com.example.timerlist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.timerlist.DONE;
import com.example.timerlist.data.List;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper

{
    private  static final int DB_VERSION = 1;
    private static final String DB_NAME = "List.db";
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // 카테고리
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT NOT NULL, " +
                "time INTEGER NOT NULL, " +
                "important INTEGER NOT NULL, " +
                "writeDate TEXT NOT NULL," +
                "category TEXT NOT NULL ," +
                "done INTEGER DEFAULT '"+ DONE.DEFAULT +"')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    //목록 조회 +  카테고리
    public ArrayList<List> getTodoList(){
        ArrayList<List> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDate DESC", null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int time = cursor.getInt(cursor.getColumnIndex("time"));
                int important = cursor.getInt(cursor.getColumnIndex("important"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
                String category = cursor.getString(cursor.getColumnIndex("category"));

                List todoItem = new List(id,content,time,important,writeDate,category);
                todoItems.add(todoItem);
            }
        }
        cursor.close();

        return  todoItems;
    }

    //카테고리 목록 조회
    public ArrayList<List> getCatrgoryDoList(String mCategory){
        ArrayList<List> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList WHERE category='"+mCategory+"'"
                , null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int time = cursor.getInt(cursor.getColumnIndex("time"));
                int important = cursor.getInt(cursor.getColumnIndex("important"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
                String category = cursor.getString(cursor.getColumnIndex("category"));

                List todoItem = new List(id,content,time,important,writeDate,category);
                todoItems.add(todoItem);
            }
        }
        cursor.close();

        return  todoItems;
    }

    //오늘 할 일 목록 조회
    public ArrayList<List> getTodayDoList( ){
        ArrayList<List> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList WHERE done=100;"
                , null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int time = cursor.getInt(cursor.getColumnIndex("time"));
                int important = cursor.getInt(cursor.getColumnIndex("important"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
                String category = cursor.getString(cursor.getColumnIndex("category"));

                List todoItem = new List(id,content,time,important,writeDate,category);
                todoItems.add(todoItem);
            }
        }
        cursor.close();

        return  todoItems;
    }
    
    public List getLastList(){
        List todoItem = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY ID DESC LIMIT 1", null);
        cursor.moveToFirst();
        if(cursor.getCount() !=0){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            int time = cursor.getInt(cursor.getColumnIndex("time"));
            int important = cursor.getInt(cursor.getColumnIndex("important"));
            String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
            String category = cursor.getString(cursor.getColumnIndex("category"));

             todoItem = new List(id,content,time,important,writeDate,category);
        }
        return todoItem;
    }



    // 목록 추가 + 카테고리
    public void InsertTodo(String _content, int _time, int _important, String _writeDate, String _category){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (content, time ,important, writeDate,category) " +
                "VALUES('" + _content + "','" + _time +"','" + _important + "' ,'" + _writeDate + "','" + _category+"' );");
    }


    // 목록 수정 + 카테고리
    public void UpdateSimpleTodo(String _content, int _time,String _beforeDate,String _category) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET content='" + _content + "'" +
                ",time = '" + _time + "' WHERE category='"+_category+"'"+
                ", WHERE writeDate='" + _beforeDate + "'");
    }

    // 오늘 할 일 수정
    public void UpdateDoTodo(DONE done, String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET done ="+done+", WHERE writeDate='" + _beforeDate + "'");
    }

    // 목록 삭제
    public void deleteTodo(String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate = '" +_beforeDate + "'");
    }
}
