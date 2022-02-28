package com.example.salebuy.Handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.salebuy.Models.Orders;
import com.example.salebuy.Models.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerDatabase extends SQLiteOpenHelper{
    final String TAG = OrderManagerDatabase.class.getSimpleName();
    final static  String DB_NAME = "Orders.db";
    final static int DB_VERSION = 1;

    public OrderManagerDatabase(@Nullable  Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table orders " +
                        "(id INTEGER primary key autoincrement," +
                        "order_email text, " +
                        "product_position int)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP table if exists orders");

        onCreate(sqLiteDatabase);

    }

    public boolean addOrder(Orders order){

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("order_email",order.getEmail());
        values.put("product_position",order.getPosition());
        long id = db.insert("orders",null,values);
        if(id <= 0){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Orders> getOrders(){

        ArrayList<Orders> orderList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT order_email,product_position FROM orders",null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                Orders order = new Orders();
                order.setEmail(cursor.getString(0));
                order.setPosition(Integer.parseInt(cursor.getInt(1) + ""));
                orderList.add(order);
            }
        }
        cursor.close();
        db.close();

        return orderList;
    }
}