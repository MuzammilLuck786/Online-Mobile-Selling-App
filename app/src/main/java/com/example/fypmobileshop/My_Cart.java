package com.example.fypmobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class My_Cart extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;

    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> cartModelList;

    TextView overTotalBill;

    AppCompatButton placeorder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);


        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        placeorder=findViewById(R.id.order);

        overTotalBill=findViewById(R.id.totalBill);

        recyclerView=findViewById(R.id.cartrecyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        cartModelList=new ArrayList<>();
        myCartAdapter=new MyCartAdapter(getApplicationContext(),cartModelList);
        recyclerView.setAdapter(myCartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot :task.getResult().getDocuments()){
                                String documentId=documentSnapshot.getId();
                                MyCartModel cartModel=documentSnapshot.toObject(MyCartModel.class);
                                cartModel.setDocumentId(documentId);
                                cartModelList.add(cartModel);
                                myCartAdapter.notifyDataSetChanged();
                            }

                            calculateTotalAmount(cartModelList);
                        }
                    }
                });

    }


    public void calculateTotalAmount(List<MyCartModel> cartModelList) {

        double totalAmount=0.0;
        for (MyCartModel myCartModel: cartModelList){
            totalAmount += myCartModel.getTotalPrice();
            overTotalBill.setText("Total Bill: " +totalAmount+" PKR");
        }

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),customerdetails.class);
                intent.putExtra("itemlist", (Serializable) cartModelList);
                startActivity(intent);
            }
        });

    }

}