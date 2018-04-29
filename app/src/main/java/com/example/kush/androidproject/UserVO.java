package com.example.kush.androidproject;

/**
 * Created by kush on 2018. 4. 23..
 */

public class UserVO {
    private int _id;
    private boolean inout;
    private int price;
    private String date;

    public UserVO(int _id, boolean inout, int price, String date) {
        this._id = _id;
        this.inout = inout;
        this.price = price;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public boolean isInout() {
        return inout;
    }

    public void setInout(boolean inout) {
        this.inout = inout;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
