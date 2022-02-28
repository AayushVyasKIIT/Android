package com.example.salebuy.Adapters;

import android.content.Context;
import android.content.Intent;
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

public class CartRecycler extends RecyclerView.Adapter<CartRecycler.viewHolder>{
    private final String email;
    private String TAG = MainActivity.class.getSimpleName();
    JSONArray jsonArray;
    Context context;
    public CartRecycler(JSONArray jsonArray, Context context,String email) {
        this.jsonArray = jsonArray;
        this.context = context;
        this.email = email;
    }
    @NonNull
    @Override
    public CartRecycler.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        return  new CartRecycler.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecycler.viewHolder holder, int position) {
        try {
            JSONObject items = jsonArray.getJSONObject(position);
            holder.title.setText(items.getString("title"));
            float price = (float)items.getDouble("price");
            holder.price.setText("$" + Float.toString(price));
//            JSONObject rating = items.optJSONObject("rating");
//            holder.ratingBar.setRating((float) rating.getDouble("rate"));
            Glide.with(context)
                    .load(items.getString("image"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
//           Bitmap bmp = BitmapFactory.decodeStream(new URL( items.getString("image")).openStream());
//           holder.image.setImageBitmap(bmp);

            //onclicklistener here
            holder.ItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Toast.makeText(context, items.getString("title"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, itemDetailView.class);
                        intent.putExtra("items", items.toString());
                        intent.putExtra("title", items.getString("title"));
                        float price = (float)items.getDouble("price");
                        intent.putExtra("price", items.getString("price"));
                        JSONObject rating = items.optJSONObject("rating");
                        intent.putExtra("ratingCount",rating.getString("count") );
                        intent.putExtra("ratingBar",(float) rating.getDouble("rate"));

                        intent.putExtra("imageURL", items.getString("image"));
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
        TextView title, price;
        RatingBar ratingBar;
        CardView ItemCard;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.CartViewImage);
            title = itemView.findViewById(R.id.CartViewTitle);
            price = itemView.findViewById(R.id.CartViewPrice);
            ratingBar = itemView.findViewById(R.id.CartViewRatingStars);
            ItemCard = itemView.findViewById(R.id.CartViewCard);
        }

    }


}
