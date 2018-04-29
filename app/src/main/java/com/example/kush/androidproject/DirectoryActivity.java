package com.example.kush.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DirectoryActivity extends AppCompatActivity{

    ListView list;

    DBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    Adapter adapter;

    Button date[], add;

    ArrayList<DetailsVO> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");

        array = new ArrayList<>();

        helper = new DBHelper(this, id);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("select inout, price, date from tb_" + id + " order by _id", null);

        while(cursor.moveToNext()){
            DetailsVO vo = new DetailsVO();

            vo.set_id(cursor.getInt(0));
            vo.setInout(cursor.getInt(1)==1);
            vo.setPrice(cursor.getInt(2));
            vo.setDate(cursor.getString(3));

            array.add(vo);
        }
        db.close();

        list = (ListView) findViewById(R.id.listItems);

    }
}
