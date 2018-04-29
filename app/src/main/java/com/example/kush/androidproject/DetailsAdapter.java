package com.example.kush.androidproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kush on 2018. 4. 29..
 */

public class DetailsAdapter extends ArrayAdapter<DetailsVO> {

    Context context;
    int resID;
    ArrayList<DetailsVO> datas;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 인자값으로 들어온 ConvertView가 null => 새로만듦
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resID, null);

            DetailsHolder holder = new DetailsHolder(convertView);
            convertView.setTag(holder);
        }
        DetailsHolder holder = (DetailsHolder)convertView.getTag();

        TextView date = holder.date;
        TextView inout = holder.inout;
        TextView price = holder.price;

        // 아래에서 생성자에의해 초기화된 datas에서 알맞은 index의 vo를 추출
        final DetailsVO vo = datas.get(position);
        date.setText(vo.getDate());
        inout.setText(vo.isInout()?"입금":"출금");
        price.setText(vo.getPrice()+"원");

        return convertView;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    public DetailsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DetailsVO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resID = resource;
        this.datas = objects;
    }
}
