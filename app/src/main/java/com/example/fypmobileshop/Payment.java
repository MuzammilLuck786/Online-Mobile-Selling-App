package com.example.fypmobileshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button openwhatsaap=findViewById(R.id.whatsapp);

        openwhatsaap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact= "+92 3470593012";
                String url="http://api.whatsapp.com/send?phone="+ contact;
                try {
                    PackageManager pm= getApplicationContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(Payment.this, "Whatsapp is not installed in your phone", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }
}