package com.example.salebuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.salebuy.Adapters.CartRecycler;
import com.example.salebuy.Adapters.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class CartActivity extends AppCompatActivity {
    final String TAG  = CartActivity.class.getSimpleName();
    JSONArray Allitems = new JSONArray();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        try {
           Allitems = new JSONArray(getIntent().getStringExtra("Allitems"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_cart);
        /*
            TODO:
            1. Intent s email yaha bulana h
            2. all orders mein position ki madad s ek json array bnana h jisme wohi items hongey
                jiski positions all orders mein hogi.
         */
        try {
            email = getIntent().getStringExtra("email");
            CartRecycler adapter = new CartRecycler(Allitems, CartActivity.this, email);
            RecyclerView recyclerView = findViewById(R.id.CartRecyclerView);
            recyclerView.setAdapter(adapter);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(CartActivity.this,3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }catch (Exception e){
            Log.e(TAG,"Cart layout k time error");
        }

    }
}