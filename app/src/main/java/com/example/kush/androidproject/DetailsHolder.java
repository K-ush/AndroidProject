package com.example.kush.androidproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kush on 2018. 4. 29..
 */

public class DetailsHolder {
   public TextView date, inout, price;

    public DetailsHolder(View root){
        date = (TextView)root.findViewById(R.id.showDate);
        inout = (TextView)root.findViewById(R.id.inout);
        price = (TextView)root.findViewById(R.id.price);

    }
}
