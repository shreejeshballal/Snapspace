package com.example.venuebooking.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.venuebooking.R;

import com.example.venuebooking.models.VenueModel;
import com.example.venuebooking.ui.VenueDescription;

import java.util.List;

public class VenueAdpaters extends RecyclerView.Adapter<VenueAdpaters.ViewHolder> {
    private Context context;
    private List<VenueModel> venueModelList;
    private RecyclerView recyclerView;

    public VenueAdpaters(Context context, List<VenueModel> venueListList,RecyclerView recyclerView) {
        this.context = context;
        this.venueModelList = venueListList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_layout,parent,false);
    return  new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VenueModel venue = venueModelList.get(position);
        Glide.with(context).load(venueModelList.get(position).getImg_url()).into(holder.venue_img);
        holder.venue_name.setText(venueModelList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), VenueDescription.class);
                intent.putExtra("venue_desc", venue.getDesc());
                intent.putExtra("venue_img",venue.getImg_url());
                intent.putExtra("venue_name",venue.getName());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return venueModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView venue_img;
        TextView venue_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            venue_img = itemView.findViewById(R.id.venue_img);
            venue_name = itemView.findViewById(R.id.venue_name);
        }
    }
}
