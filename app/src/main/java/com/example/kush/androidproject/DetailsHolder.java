package com.example.kush.androidproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kush on 2018. 4. 29..
 */

// 성능개선 ListView 에 필요한 HOLDER 구현

public class DetailsHolder {
   public TextView date, inout, price;

    public DetailsHolder(View root){
        date = (TextView)root.findViewById(R.id.showDate);
        inout = (TextView)root.findViewById(R.id.inout);
        price = (TextView)root.findViewById(R.id.price);

    }
}
