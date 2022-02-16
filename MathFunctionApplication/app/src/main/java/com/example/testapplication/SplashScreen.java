 package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //creating the intent and telling from where to where intent
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    //starting the intent
                    startActivity(intent);
                    //finishing the previous activity completely.
                    finish();
                }
            }
        }; thread.start();

    }
}