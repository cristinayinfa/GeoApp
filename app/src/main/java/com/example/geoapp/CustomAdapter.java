package com.example.geoapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Timer timer;
    Activity activity;
    private ArrayList loc_id, loc_address, loc_long, loc_lat, locationSource;
    int position;

    CustomAdapter(Context context, Activity activity, ArrayList loc_id,ArrayList loc_address, ArrayList loc_long, ArrayList loc_lat ){
        this.context = context;
        this.activity = activity;
        this.loc_id = loc_id;
        this.loc_address = loc_address;
        this.loc_long = loc_long;
        this.loc_lat = loc_lat;
    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    // When user clicks on a specific item to view, update or delete
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        this.position = position;
        holder.loc_id_text.setText(String.valueOf(loc_id.get(position)));
        holder.loc_addr_text.setText(String.valueOf(loc_address.get(position)));
        holder.loc_long_text.setText(String.valueOf(loc_long.get(position)));
        holder.loc_lat_text.setText(String.valueOf(loc_lat.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(loc_id.get(position)));
                intent.putExtra("address", String.valueOf(loc_address.get(position)));
                intent.putExtra("longitude", String.valueOf(loc_long.get(position)));
                intent.putExtra("latitude", String.valueOf(loc_lat.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return loc_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView loc_id_text, loc_addr_text, loc_long_text, loc_lat_text;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            loc_id_text = itemView.findViewById(R.id.loc_id_text);
            loc_addr_text = itemView.findViewById(R.id.loc_addr_text);
            loc_long_text = itemView.findViewById(R.id.loc_long_text);
            loc_lat_text = itemView.findViewById(R.id.loc_lat_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
    
}
