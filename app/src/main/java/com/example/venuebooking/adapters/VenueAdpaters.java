package com.example.venuebooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.venuebooking.R;
import com.example.venuebooking.models.VenueModel;

import java.util.List;

public class VenueAdpaters extends RecyclerView.Adapter<VenueAdpaters.ViewHolder> {
    private Context context;
    private List<VenueModel> venueModelList;

    public VenueAdpaters(Context context, List<VenueModel> venueListList) {
        this.context = context;
        this.venueModelList = venueListList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_list,parent,false);
    return  new ViewHolder(view);
        //        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(venueModelList.get(position).getImg_url()).into(holder.venue_img);
        holder.venue_name.setText(venueModelList.get(position).getName());
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
