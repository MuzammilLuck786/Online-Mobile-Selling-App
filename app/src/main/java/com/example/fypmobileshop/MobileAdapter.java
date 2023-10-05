package com.example.fypmobileshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.ViewHolder> {

    private final Context context;
    private List<MobileModel> mobileModelList;

    public MobileAdapter(Context context, List<MobileModel> mobileModelList) {
        this.context = context;
        this.mobileModelList = mobileModelList;
    }

    @NonNull
    @Override
    public MobileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_lists,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MobileAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(mobileModelList.get(position).getPhoneimage()).into(holder.Phoneimage);
        holder.Phonename.setText(mobileModelList.get(position).getPhonename());
        holder.Phoneprice.setText("Price:"+mobileModelList.get(position).getPhoneprice() +" PKR");
        holder.Phonediscount.setText(mobileModelList.get(position).getPhonediscount());
        holder.Storage.setText(mobileModelList.get(position).getStorage());
        holder.Screen.setText(mobileModelList.get(position).getScreen());
        holder.Battery.setText(mobileModelList.get(position).getBattery());
        holder.Allcameras.setText(mobileModelList.get(position).getAllcameras());
        holder.Frontcamera.setText(mobileModelList.get(position).getFrontcamera());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,DetailAcitvity.class);
                intent.putExtra("detail",mobileModelList.get(holder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mobileModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Phoneimage;
        TextView Phonename,Phoneprice,Phonediscount,Storage,Screen,Battery,Allcameras,Frontcamera;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Phoneimage=itemView.findViewById(R.id.Phoneimage);
            Phonename=itemView.findViewById(R.id.Phonename);
            Phoneprice=itemView.findViewById(R.id.Phoneprice);
            Phonediscount=itemView.findViewById(R.id.Phonediscount);
            Storage=itemView.findViewById(R.id.Storage);
            Screen=itemView.findViewById(R.id.Screen);
            Battery=itemView.findViewById(R.id.Battery);
            Allcameras=itemView.findViewById(R.id.Allcameras);
            Frontcamera=itemView.findViewById(R.id.Frontcamera);
        }

    }
}
