package com.example.kush.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kush on 2018. 4. 23..
 */

public class DBHelper extends SQLiteOpenHelper {

    final static int DATABASE_VERSION = 1;
    String name;

    public DBHelper(Context context, String name) {
        super(context, "codeDB", null, DATABASE_VERSION);
        this.name = name;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table tb_" + name + "("+
                     "  _id         integer primary key autoincrement," +
                     "  userid      unique not null," +
                     "  userpass    not null," +
                     "  name        not null)";

        db.execSQL(sql);


        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i1 == DATABASE_VERSION){
            db.execSQL("drop table tb_user");
            onCreate(db);
        }
    }
}
