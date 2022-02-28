package com.example.salebuy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.salebuy.MainActivity;
import com.example.salebuy.R;
import com.example.salebuy.itemDetailView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.viewHolder>{
    private String TAG = MainActivity.class.getSimpleName();
    JSONArray jsonArray;
    Context context;
    String email;
    public RecyclerAdapter(JSONArray jsonArray, Context context, String email) {
        this.jsonArray = jsonArray;
        this.context = context;
        this.email = email;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return  new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        try {
            JSONObject items = jsonArray.getJSONObject(position);
            holder.title.setText(items.getString("title"));
            float price = (float)items.getDouble("price");
            holder.price.setText("$" + Float.toString(price));
            JSONObject rating = items.optJSONObject("rating");
            holder.ratingCount.setText(rating.getString("count"));
            holder.ratingBar.setRating((float) rating.getDouble("rate"));
            Glide.with(context)
                    .load(items.getString("image"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
//            Log.e(TAG,items.getString("image"));
//           Bitmap bmp = BitmapFactory.decodeStream(new URL( items.getString("image")).openStream());
//           holder.image.setImageBitmap(bmp);

            //onclicklistener here
            holder.ItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Toast.makeText(context, items.getString("title"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, itemDetailView.class);
                        intent.putExtra("email",email);
                        intent.putExtra("items", items.toString());
                        intent.putExtra("Allitems",jsonArray.toString());
                        intent.putExtra("position",Integer.toString(position));
//                        intent.putExtra("title", items.getString("title"));
//                        float price = (float)items.getDouble("price");
//                        intent.putExtra("price", items.getString("price"));
//                        JSONObject rating = items.optJSONObject("rating");
//                        intent.putExtra("ratingCount",rating.getString("count") );
//                        intent.putExtra("ratingBar",(float) rating.getDouble("rate"));
//
//                        intent.putExtra("imageURL", items.getString("image"));
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        Log.e(TAG,"Intent mein kch hua");
                    }
                }
            });

        }catch (Exception e) {
            Log.e(TAG,"adapter k binder mein problem");
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }



    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title, ratingCount, price;
        RatingBar ratingBar;
        CardView ItemCard;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.itemImage);
            title = itemView.findViewById(R.id.Title);
            ratingCount = itemView.findViewById(R.id.RatingCount);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.RatingStars);
            ItemCard = itemView.findViewById(R.id.ItemCard);
        }

    }


}
