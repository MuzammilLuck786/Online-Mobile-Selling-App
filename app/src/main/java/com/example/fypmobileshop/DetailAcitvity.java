package com.example.fypmobileshop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailAcitvity extends AppCompatActivity {

    ImageView Detailimage,Additem,Removeitem;
    TextView Detailname,Detailprice,Detaildiscount,DetailStorage,DetailScreen,DetailBattery,DetailAllcameras,DetailFrontcamera,Itemcount;
    int totalQuantity = 1;
    int totalPrice = 0;
    Button Addtocart;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    MobileModel mobileModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acitvity);

        final Object object=getIntent().getSerializableExtra("detail");
        if (object instanceof MobileModel){
            mobileModel = (MobileModel) object;

        }

        Detailimage=findViewById(R.id.Detailimage);
        Detailname=findViewById(R.id.Detailname);
        Detailprice=findViewById(R.id.Detailprice);
        Detaildiscount=findViewById(R.id.Detaildiscount);
        DetailStorage=findViewById(R.id.DetailStorage);
        DetailScreen=findViewById(R.id.DetailScreen);
        DetailBattery=findViewById(R.id.DetailBattery);
        DetailAllcameras=findViewById(R.id.DetailAllcameras);
        DetailFrontcamera=findViewById(R.id.DetailFrontcamera);
        Additem=findViewById(R.id.Additem);
        Removeitem=findViewById(R.id.Removeitem);
        Itemcount=findViewById(R.id.Itemcount);
        Addtocart=findViewById(R.id.Addtocart);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        if (mobileModel != null){
            Glide.with(getApplicationContext()).load(mobileModel.getPhoneimage()).into(Detailimage);
            Detailname.setText(mobileModel.getPhonename());
            Detailprice.setText("Price:"+mobileModel.getPhoneprice()+ " PKR");
            Detaildiscount.setText(mobileModel.getPhonediscount());
            DetailStorage.setText(mobileModel.getStorage());
            DetailScreen.setText(mobileModel.getScreen());
            DetailBattery.setText(mobileModel.getBattery());
            DetailAllcameras.setText(mobileModel.getAllcameras());
            DetailFrontcamera.setText(mobileModel.getFrontcamera());

            totalPrice = mobileModel.getPhoneprice()*totalQuantity;

        }

        Addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });
        Additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 9){
                    totalQuantity++;
                    Itemcount.setText(String.valueOf(totalQuantity));
                    totalPrice = mobileModel.getPhoneprice()*totalQuantity;
                }
            }
        });
        Removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity > 1){
                    totalQuantity--;
                    Itemcount.setText(String.valueOf(totalQuantity));
                    totalPrice = mobileModel.getPhoneprice()*totalQuantity;
                }

            }
        });
    }


    private void addedToCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calfordate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM-dd-yyyy");
        saveCurrentDate=currentDate.format(calfordate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calfordate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("phoneImage",mobileModel.getPhoneimage());
        cartMap.put("phoneName",mobileModel.getPhonename());
        cartMap.put("phonePrice",mobileModel.getPhoneprice());
        cartMap.put("phoneDiscount",mobileModel.getPhonediscount());
        cartMap.put("phoneStorage",mobileModel.getStorage());
        cartMap.put("phoneScreen",mobileModel.getScreen());
        cartMap.put("phoneBattery",mobileModel.getBattery());
        cartMap.put("phoneAllCameras",mobileModel.getAllcameras());
        cartMap.put("phoneFrontCamera",mobileModel.getFrontcamera());
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("totalPrice",totalPrice);
        cartMap.put("totalQuantity",totalQuantity);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailAcitvity.this, "Item is Added To Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}