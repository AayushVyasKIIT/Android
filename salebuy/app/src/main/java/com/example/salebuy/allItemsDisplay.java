package com.example.salebuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salebuy.Adapters.RecyclerAdapter;
import com.example.salebuy.Handlers.DatabaseHelper;
import com.example.salebuy.Handlers.HttpHandler;
import com.example.salebuy.Handlers.OrderPrefetch;
import com.example.salebuy.Models.User;
import com.example.salebuy.databinding.ActivityMainBinding;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class allItemsDisplay extends AppCompatActivity {

    ActivityMainBinding binding;

    private String TAG = MainActivity.class.getSimpleName();

    String txt ;
    TextView tv;
    JSONArray jsonArray;
    String email;
    JSONArray RelevantItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_all_items_display);
        new getJson().execute();
        email = getIntent().getStringExtra("email");
        setUserName();
        TextView viewCart = (TextView) findViewById(R.id.AllitemsViewCart);
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPrefetch prefetch = new OrderPrefetch(jsonArray,allItemsDisplay.this,email);
                try {
                    RelevantItems = new JSONArray(prefetch.getRelevantItems());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(allItemsDisplay.this,CartActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("Allitems",RelevantItems.toString());
                startActivity(intent);

            }
        });


    }
    private void  setUserName(){
        DatabaseHelper db = new DatabaseHelper(allItemsDisplay.this);
        for(User user : db.getAllUser()){
            if(user.getEmail().equals(email)){
                TextView username = (TextView) findViewById(R.id.AllitemUsername);
                username.setText(user.getName());
                break;
            }
        }

    }
    private class getJson extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(allItemsDisplay.this, "Json Data is downloading", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = "https://fakestoreapi.com/products";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG,"Resoponse from URL: " + jsonStr);

            if(jsonStr != null){
                try{
                    jsonArray = new JSONArray(jsonStr);
                } catch (final JSONException e) {
                    Log.e(TAG,"Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(allItemsDisplay.this, "Json parsing error:  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }
        protected  void onPostExecute(Void result){
            super.onPostExecute(result);
            RecyclerAdapter adapter = new RecyclerAdapter(jsonArray,allItemsDisplay.this,email);
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setAdapter(adapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(allItemsDisplay.this);
            recyclerView.setLayoutManager(linearLayoutManager);

        }
    }
}

