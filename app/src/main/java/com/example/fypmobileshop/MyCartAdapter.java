package com.example.fypmobileshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.Viewholder> {


    Context context;
    List<MyCartModel> cartModelList;


    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;


        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Glide.with(context).load(cartModelList.get(position).getPhoneImage()).into(holder.phoneimg);
        holder.name.setText(cartModelList.get(position).phoneName);
        holder.price.setText(cartModelList.get(position).phonePrice +"PKR");
        holder.discount.setText(cartModelList.get(position).phoneDiscount);
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity()+"");
        holder.totalprice.setText(cartModelList.get(position).getTotalPrice()+"PKR");

        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(cartModelList.get(holder.getAdapterPosition()).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    cartModelList.remove(cartModelList.get(holder.getAdapterPosition()));
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    Intent intent= new Intent(context,My_Cart.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    ((My_Cart)context).finish();


                                }
                                else {
                                    Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

            }
        });


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView phoneimg;
        TextView name,price,discount,date,time,quantity,totalprice;
        Button deleteitem;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            phoneimg=itemView.findViewById(R.id.pimage);
            name =itemView.findViewById(R.id.pname);
            price=itemView.findViewById(R.id.pprice);
            discount=itemView.findViewById(R.id.pdiscount);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            quantity=itemView.findViewById(R.id.quantity);
            totalprice=itemView.findViewById(R.id.totalcost);
            deleteitem=itemView.findViewById(R.id.deleteitem);

        }
    }
}
