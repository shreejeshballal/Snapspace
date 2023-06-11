package com.example.admin.adapters;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Fragments.Admin_home;
import com.example.admin.Models.Venuelist;
import com.example.admin.R;


import java.util.ArrayList;

public class Venuelistadapter extends RecyclerView.Adapter<Venuelistadapter.myviewholder> {

    Context context;
    ArrayList<Venuelist> venueArrayList;


    public Venuelistadapter(Context context, ArrayList<Venuelist> venueArrayList) {
        this.context = context;
        this.venueArrayList = venueArrayList;
    }

    @NonNull
    @Override
    public Venuelistadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.venue_list,parent,false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Venuelist venue=venueArrayList.get(position);
        holder.venuename.setText(venue.name);

    }

    @Override
    public int getItemCount() {
        return venueArrayList.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder {
        TextView venuename;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            venuename =itemView.findViewById(R.id.venue_name);
        }
    }
}
