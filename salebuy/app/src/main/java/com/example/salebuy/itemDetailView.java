package com.example.salebuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.salebuy.Handlers.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class itemDetailView extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HttpHandler.class.getSimpleName();
    TextView title, description;
    String email;
    int position;
    JSONObject items;
    JSONArray Allitems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_view);
        email = getIntent().getStringExtra("email");
        position = Integer.valueOf(getIntent().getStringExtra("position"));
        displayThings();
        Button orderButton = (Button) findViewById(R.id.orderButton);
        try {
            orderButton.setOnClickListener(itemDetailView.this);
        }catch (Exception e){
            Log.e(TAG,"On click mein problem");
        }
     }
    public void displayThings (){
            try {
                items = new JSONObject(getIntent().getStringExtra("items"));
                title = (TextView) findViewById(R.id.detailViewTitle);
                title.setText(items.getString("title"));

                TextView price = (TextView) findViewById(R.id.detailViewPrice);
                float priceConv = (float) items.getDouble("price");
                price.setText("$" + Float.toString(priceConv));

            /*
             TODO :
               1. Rating bug needs to be fixed
             */

//            holder.ratingBar.setRating((float) rating.getDouble("rate"));
//            JSONObject rating = items.optJSONObject("rating");
//            ((TextView)findViewById(R.id.RatingCount)).setText(rating.getString("count"));
//            ((RatingBar)findViewById(R.id.RatingStars)).setRating((float) items.getDouble("rate"));

                ((TextView) findViewById(R.id.detailViewDescription)).setText(items.getString("description"));

                ImageView detailViewImage = (ImageView) findViewById(R.id.detailImageVIew);
                Glide.with(this)
                        .load(items.getString("image"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(detailViewImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.orderButton:
                    try{
                        Allitems = new JSONArray(getIntent().getStringExtra("Allitems"));
                    }catch (JSONException e){
                        Log.e(TAG,e.toString());
                    }
                    Intent intent = new Intent(this,AddOrderActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("position",Integer.toString(position));
                    intent.putExtra("Allitems",Allitems.toString());
                    intent.putExtra("items", items.toString());
                    startActivity(intent);
            }
        }
    }
