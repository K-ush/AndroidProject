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
    DetailsAdapter adapter;
    Button add;
    TextView total;

    DBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    String id;
    ArrayList<DetailsVO> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        list = (ListView) findViewById(R.id.listItems);

        // Intent와 함께 전달된 extras에서 id값 추출
        id = getIntent().getExtras().getString("user");

        //DetailsVO를 담을 array 객체 생성
        array = new ArrayList<>();

        helper = new DBHelper(this, id);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("select _id, price, date from tb_" + id + " order by _id", null);

        while(cursor.moveToNext()){
            DetailsVO vo = new DetailsVO();

            int price = cursor.getInt(1);

            vo.set_id(cursor.getInt(0));
            vo.setInout(price > 0 ? true : false);
            vo.setPrice(price > 0 ? price : (-1*price));
            vo.setDate(cursor.getString(2));

            array.add(vo);
        }

        //ListView에 붙일 Adapter 객체 초기화
        adapter = new DetailsAdapter(this, R.layout.activity_details_item, array);
        // 부착
        list.setAdapter(adapter);

        //ListView에 클릭리스터 장착 -> 요소 클릭시 수정가능한 Activity로 이동
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

        // add버튼에 클릭리스너 장착 -> 새로운 값을 db에 삽입
        add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                dataBundle.putString("dbName", id);
                dataBundle.putBoolean("isNew", true);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtras(dataBundle);
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

    @Override
    protected void onResume() {
        super.onResume();

        array.clear();
        helper = new DBHelper(this, id);
        db = helper.getReadableDatabase();

        int totalPrice = 0;
        cursor = db.rawQuery("SELECT _id, price, date, cuz from tb_" + id, null);

        while(cursor.moveToNext()){
            DetailsVO vo = new DetailsVO();

            vo.set_id(cursor.getInt(0));
            vo.setPrice(cursor.getInt(1));
            vo.setDate(cursor.getString(2));
            vo.setCuz(cursor.getString(3));

            array.add(vo);
        }
        cursor = db.rawQuery("SELECT sum(price) from tb_" + id, null);
        while(cursor.moveToNext()){
            totalPrice = cursor.getInt(0);
        }

        total.setText(totalPrice+"원");
        db.close();
        adapter.addAll(array);
        adapter.notifyDataSetChanged();
    }
}
