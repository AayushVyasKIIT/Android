package com.example.salebuy.Handlers;

import android.content.Context;
import android.util.Log;

import com.example.salebuy.Models.Orders;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OrderPrefetch {
    Context context  = null;
    String email;
    JSONArray Allitems;
    public OrderPrefetch(JSONArray Allitems, Context context,String email) {
        this.Allitems = Allitems;
        this.context = context;
        this.email = email;
    }

    final String TAG = OrderPrefetch.class.getSimpleName();

    OrderManagerDatabase orderDBhelper = new OrderManagerDatabase(context);
    public JSONArray getRelevantItems(){
        return convertOrdersToJsonArray(getOrders());

    }
    private ArrayList<Orders> getOrders(){
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
}
