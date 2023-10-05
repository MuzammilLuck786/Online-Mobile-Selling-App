package com.example.fypmobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class customerdetails extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    EditText customerName,customerPhone,customerAddress;
    Button submitDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerdetails);

        customerName=findViewById(R.id.name);
        customerPhone=findViewById(R.id.phone);
        customerAddress=findViewById(R.id.address);
        submitDetails=findViewById(R.id.submit);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=customerName.getText().toString();
                String phone =customerPhone.getText().toString();
                String address=customerAddress.getText().toString();

                if (name.isEmpty()){
                    customerName.setError("Name is Required");
                    customerName.requestFocus();
                    return;
                }
                if (phone.isEmpty()){
                    customerPhone.setError("Phone number is requred");
                    customerPhone.requestFocus();
                    return;
                }

                if (phone.length() != 11){
                    customerPhone.setError("Please enter correct 11 digit number");
                    customerPhone.requestFocus();
                    return;
                }
                if (address.isEmpty()){
                    customerAddress.setError("Address is must");
                    customerAddress.requestFocus();
                    return;
                }

                    CustomerInfoModel customerInfoModel = new CustomerInfoModel(name, phone, address);
                    uploadInfo(customerInfoModel);

            }
        });
    }

    private void uploadInfo(CustomerInfoModel customerInfoModel) {
       db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
               .collection("userInfo")
               .add(customerInfoModel)
               .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {
                       Toast.makeText(customerdetails.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(),Payment.class));
                       finish();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(customerdetails.this, "Not Send Please Try Again"+e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               });


        List<MyCartModel> list=(ArrayList<MyCartModel>)getIntent().getSerializableExtra("itemlist");

        if (list !=null && list.size() >0){
            for (MyCartModel model:list){
                final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("phoneImage",model.getPhoneImage());
                cartMap.put("phoneName",model.getPhoneName());
                cartMap.put("phonePrice",model.getPhonePrice());
                cartMap.put("phoneDiscount",model.getPhoneDiscount());
                cartMap.put("currentDate",model.getCurrentDate());
                cartMap.put("currentTime",model.getCurrentTime());
                cartMap.put("totalPrice",model.getTotalPrice());
                cartMap.put("totalQuantity",model.getTotalQuantity());

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(customerdetails.this, "Your order has been placed", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        }






    }
}