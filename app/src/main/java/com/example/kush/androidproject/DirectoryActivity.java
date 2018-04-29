package com.example.kush.androidproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DirectoryActivity extends AppCompatActivity {

    ListView list;

    DBHelper helper;
    SQLiteDatabase db;

    Button date[], add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");

        Toast.makeText(this, name, Toast.LENGTH_LONG).show();

        list = (ListView) findViewById(R.id.listItems);

    }
}
