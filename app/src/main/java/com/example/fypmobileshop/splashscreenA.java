package com.example.fypmobileshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashscreenA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Intent intent=new Intent(splashscreenA.this,Chose_One.class);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          startActivity(intent);
                                          finish();
                                      }
                                  },
                3000);

    }
}