package com.example.kush.androidproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    EditText editPrice, editCuz;
    Button btnSave, btnUpdate, btnDelete;

    DBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    int itemId;
    boolean isNew;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = new Intent();

        editPrice = (EditText)findViewById(R.id.editPrice);
        editCuz = (EditText)findViewById(R.id.editCuz);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        helper = new DBHelper(this, id);

        Bundle extras = getIntent().getExtras();
        id = extras.getString("dbName");
        if(extras != null){
            isNew = extras.getBoolean("isNew");
            if(isNew) {
                btnUpdate.setVisibility(View.INVISIBLE);
                btnDelete.setVisibility(View.INVISIBLE);
            } else{
                itemId = extras.getInt("id");
                id = extras.getString("dbName");
                btnSave.setVisibility(View.INVISIBLE);

                db = helper.getWritableDatabase();
                id="kush";
                String strSQL = "SELECT price, cuz FROM tb_"+ id + " where _id = " + Integer.toString(itemId) + ";";

                cursor = db.rawQuery(strSQL, null);

                DetailsVO vo = new DetailsVO();

                while(cursor.moveToNext()){

                    int price = cursor.getInt(0);
                    vo.setInout(price > 0 ? true : false);
                    vo.setPrice(price > 0 ? price : (-1*price));
                    vo.setCuz(cursor.getString(1));
                }

                cursor = db.rawQuery("select sum(price) from tb_" + id, null);

                editPrice.setText(vo.getPrice()+"");
                editCuz.setText(vo.getCuz());

                db.close();
            }
        }
    }

    public void save(View view){

        String price, cuz;

        price = editPrice.getText().toString();
        cuz = editCuz.getText().toString();

        db = helper.getWritableDatabase();

        String strSQL = "insert into tb_" + id + " (price, date, cuz) values(?,?,?)";
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int date = Calendar.getInstance().get(Calendar.DATE);
        db.execSQL(strSQL, new String[]{price, month + "/" + date , cuz});

        db.close();

        Toast.makeText(getApplicationContext(), "추가되었습니다", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void update(View view){

        String price, cuz;

        price = editPrice.getText().toString();
        cuz = editCuz.getText().toString();

        db = helper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("price", price);
        content.put("cuz", cuz);
        db.update("tb_"+ id, content, "_id = ?", new String[]{Integer.toString(itemId)});

        db.close();

        Toast.makeText(getApplicationContext(), "변경되었습니다", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void delete(View view){

        db = helper.getWritableDatabase();

        db.delete("tb_" + id, "_id = ?", new String[]{Integer.toString(itemId)});

        db.close();

        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();

        finish();
    }
}
