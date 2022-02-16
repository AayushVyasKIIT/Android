package com.example.testapplication;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    EditText et;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hides the action bar at the top of the screen.
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b =(Button) findViewById(R.id.button);
        MathFunc MathObj = new MathFunc();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et = (EditText) findViewById(R.id.et);
                spinner = (Spinner) findViewById(R.id.spinner);
                String Mfunc = spinner.getSelectedItem().toString();
                System.out.println(Mfunc);
                int ans = 0, value;
                switch (Mfunc){
                    case "factorial" :
                        try {
                            value = Integer.parseInt(et.getText().toString());
                            ans= MathObj.factorial(value);

                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            tv = (TextView) findViewById(R.id.tv1);
                            tv.setText(Integer.toString(ans));
                            tv.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "fib" :

                        try {
                            value = Integer.parseInt(et.getText().toString());
                            ans= MathObj.fib(value);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            tv = (TextView) findViewById(R.id.tv1);
                            tv.setText(Integer.toString(ans));
                            tv.setVisibility(View.VISIBLE);
                        }
                        break;

                    default:
                        tv = (TextView) findViewById(R.id.tv1);
                        System.out.println("error");
                        tv.setText("error");
                        tv.setVisibility(View.VISIBLE);
                        break;
                    }


                Toast t = new Toast(MainActivity.this);
                t.setText("Caluclation done");
                t.show();


            }

        });




    }
}