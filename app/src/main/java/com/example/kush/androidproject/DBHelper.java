package com.example.kush.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kush on 2018. 4. 23..
 */

public class DBHelper extends SQLiteOpenHelper {

    final static int DATABASE_VERSION = 2;
    String id;

    public DBHelper(Context context, String id) {
        super(context, "codeDB", null, DATABASE_VERSION);
        this.id = id;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table tb_" + id + "(" +
                "  _id         integer primary key autoincrement," +
                "  price       integer not null," +
                "  date," +
                "  cuz)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i1 == DATABASE_VERSION) {
            db.execSQL("drop table tb_" + id);
            onCreate(db);
        }
    }
}
