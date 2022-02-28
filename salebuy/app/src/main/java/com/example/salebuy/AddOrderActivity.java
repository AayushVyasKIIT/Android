package com.example.salebuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.salebuy.Handlers.OrderManagerDatabase;
import com.example.salebuy.Models.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class AddOrderActivity extends AppCompatActivity {
    private  String TAG = AddOrderActivity.class.getSimpleName();
    String email; int position;
    JSONObject items;
    OrderManagerDatabase orderDBhelper;
    JSONArray Allitems;
    Orders order;
    JSONArray RelevantItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        email = getIntent().getStringExtra("email");
        position = Integer.valueOf(getIntent().getStringExtra("position"));
        iniObjects();



        displayOrder();
        TextView viewCart = (TextView) findViewById(R.id.AddOrderViewCarButton);



        //primary key will come through intent (Came through intent above ( email))
        /*
        TODO
            1. primary key will come through intent (Came through intent above ( email))
            2.  call database helper and add item to userItems table here
         */
        Log.e(TAG, String.valueOf(Integer.valueOf(position)));

        addOrder();
        getRelevantOrders();
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddOrderActivity.this,CartActivity.class);
                intent.putExtra("Allitems",RelevantItems.toString());
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

    }
    private void iniObjects(){
        order = new Orders();
        orderDBhelper= new OrderManagerDatabase(AddOrderActivity.this);
    }
    private  void addOrder(){
        order.setEmail(email);
        order.setPosition(position);
        try {
            if(orderDBhelper.addOrder(order)){
                Log.e(TAG,"Order Placed");
                TextView Confirmation = (TextView) findViewById(R.id.OrderConfirmation);
                Confirmation.setText("Order placed successfully");
            }else{
                TextView Confirmation = (TextView) findViewById(R.id.OrderConfirmation);
                Confirmation.setText("Failed to place an order");
            }
        }catch (Exception e){
            Log.e(TAG,"Order place krty wqt problem");
            TextView Confirmation = (TextView) findViewById(R.id.OrderConfirmation);
            Confirmation.setText("Failed to place an order");
        }

    }
    private ArrayList<Orders>  getOrders(String email){
        ArrayList<Orders> EmailOrdersRelationship = new ArrayList<>();
        try {
            List<Orders> allorders = orderDBhelper.getOrders();

            for (Orders each : allorders) {

                if(each.getEmail().equals(email)){
                    EmailOrdersRelationship.add(each);
                }
            }
        }catch (Exception e){
            Log.e(TAG,"listing all orders mein error");
        }
        return EmailOrdersRelationship;
    }
    private JSONArray convertOrdersToJsonArray(ArrayList<Orders> EmailOrders){
        try{
            Allitems = new JSONArray(getIntent().getStringExtra("Allitems"));
        } catch (JSONException e) {
            Log.e(TAG,"Error intenting JSON on Add Order");
        }
        JSONArray relevantItems = new JSONArray();
        for(Orders each: EmailOrders){
            try {
                relevantItems.put(Allitems.getJSONObject(each.getPosition()));
            } catch (JSONException e) {
                Log.e(TAG,"relevant Items mein error");
            }
        }
        return relevantItems;
    }
    private void displayOrder() {
        try {
            items = new JSONObject(getIntent().getStringExtra("items"));
            TextView title = (TextView) findViewById(R.id.OrderTitle);
            title.setText(items.getString("title"));
            ImageView detailViewImage = (ImageView) findViewById(R.id.OrderConfirmationImageView);
            Glide.with(this)
                    .load(items.getString("image"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(detailViewImage);
            TextView orderConfirmation = (TextView)findViewById(R.id.OrderConfirmation);
            orderConfirmation.setText("Thanks! Your order has been successfully placed. Click on cart to view details");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void getRelevantOrders(){
        ArrayList<Orders> EmailOrders =getOrders(email);
        try {
            RelevantItems = convertOrdersToJsonArray(EmailOrders);
//            for (int position = 0 ; position < RelevantItems.length();position++) {
//                try {
//                    JSONObject ItemOrder = RelevantItems.getJSONObject((int) position);
//                    Log.e(TAG,ItemOrder.getString("title"));
//                } catch (JSONException e) {
//                    Log.e(TAG,"Main cart function mein problem");
//                }
//            }
        }catch (Exception e){
            Log.e(TAG,"Error in main");
        }

    }

}