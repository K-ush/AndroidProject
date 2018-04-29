package com.example.kush.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DirectoryActivity extends AppCompatActivity{

    ListView list;

    DBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    DetailsAdapter adapter;

    Button add;

    TextView total;

    String id;

    ArrayList<DetailsVO> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        array = new ArrayList<>();

        helper = new DBHelper(this, id);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("select price, date from tb_" + id + " order by _id", null);

        while(cursor.moveToNext()){
            DetailsVO vo = new DetailsVO();

            int price = cursor.getInt(1);

            vo.set_id(cursor.getInt(0));
            vo.setInout(price > 0 ? true : false);
            vo.setPrice(price > 0 ? price : (-1*price));
            vo.setDate(cursor.getString(2));

            array.add(vo);
        }

        list = (ListView) findViewById(R.id.listItems);
        adapter = new DetailsAdapter(this, R.layout.activity_details_item, array);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DetailsVO vo = array.get(position);

                Bundle databundle = new Bundle();
                databundle.putInt("id", vo.get_id());
                databundle.putString("dbName", id);
                databundle.putBoolean("isNew", false);

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtras(databundle);
                startActivity(intent);
            }
        });

        add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                dataBundle.putBoolean("isNew", true);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtras(dataBundle);
                intent.putExtra("dbName", id);
                startActivity(intent);
            }
        });
        int totalPrice = 0;
        cursor = db.rawQuery("select sum(price) from tb_" + id, null);
        while(cursor.moveToNext()) {
            totalPrice = cursor.getInt(0);
        }
        total = (TextView)findViewById(R.id.totalPrice);
        total.setText(totalPrice+"원");


        db.close();
    }
}
