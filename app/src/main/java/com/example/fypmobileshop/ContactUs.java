package com.example.fypmobileshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {

    Button callNow,sendMail,share;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        callNow=findViewById(R.id.callNow);
        sendMail=findViewById(R.id.sendMail);
        linearLayout=findViewById(R.id.linearLayout2);
        share=findViewById(R.id.shareIt);


        callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:03016076656"));
                startActivity(i);
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto","lucksahib786@gmail.com",null));
                startActivity(i2);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q=Aziz Markeet Burewala"));
                startActivity(i3);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4=new Intent(Intent.ACTION_SEND);
                i4.setType("text/Plan");
                i4.putExtra(Intent.EXTRA_SUBJECT,"Hello Mobile Shop");
                i4.putExtra(Intent.EXTRA_TEXT,"Your application link here");
                startActivity(Intent.createChooser(i4,"Share Via"));
            }
        });

    }
}