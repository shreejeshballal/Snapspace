package com.example.admin.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Models.BookingModel;
import com.example.admin.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class  Booking_Adapter extends RecyclerView.Adapter<Booking_Adapter.ViewHolder> {

    private  List<BookingModel> bookingModelList;
    Context context;
    private RecyclerView recyclerView;

    public Booking_Adapter(Context context, List<BookingModel> bookingModelList,RecyclerView recyclerView) {
        Log.d("bookingDebug", "Constructor inside");
        this.bookingModelList = bookingModelList;
        this.context = context;
        this.recyclerView=recyclerView;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("bookingDebug", "Entered on create viewholder");
        View view= LayoutInflater.from(context).inflate(R.layout.booking_instance_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Booking_Adapter.ViewHolder holder, int position) {
        BookingModel booking = bookingModelList.get(position);
        Log.d("bookingDebug", String.valueOf(booking));
        holder.venueName.setText(bookingModelList.get(position).getName());
        holder.cost.setText(bookingModelList.get(position).getCost());
        holder.eventTitle.setText(bookingModelList.get(position).getVenueName());
        holder.slot.setText(bookingModelList.get(position).getSlot());

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("d/M/yyyy");
            Date date = inputFormat.parse(booking.getDate());

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            assert date != null;
            String day = dayFormat.format(date);
            holder.day.setText(day);

            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            String month = monthFormat.format(date);
            holder.month.setText(month);

            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            String year = yearFormat.format(date);
            holder.year.setText(year);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean payment = booking.isPayment();
        if(payment)
        {
            holder.colorLayout.setBackgroundResource(R.drawable.booking_purple_bg);
        }else {
            holder.colorLayout.setBackgroundResource(R.drawable.booking_red_bg);

        }


//        holder.itemView.setOnClickListener(view -> {
//
//            Intent intent = new Intent(view.getContext(), BookingDescription.class);
//            view.getContext().startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        Log.d("bookingDebug", "Item count received");
        return bookingModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout colorLayout;
        TextView venueName,slot,eventTitle,cost,day,month,year;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            venueName = itemView.findViewById(R.id.bookedVenue);
            slot = itemView.findViewById(R.id.bookedSlot);
            eventTitle = itemView.findViewById(R.id.bookedTitle);
            cost = itemView.findViewById(R.id.bookedCost);
            day = itemView.findViewById(R.id.bookedDateDay);
            month = itemView.findViewById(R.id.bookedDateMonth);
            year = itemView.findViewById(R.id.bookedDateYear);
            colorLayout = itemView.findViewById(R.id.booking_linear_layout);

        }
    }
    //@SuppressLint("NotifyDataSetChanged")
    public void setData(List<BookingModel> newDataList) {
        Log.d("bookingDebug", "Notify data set changed");
        bookingModelList = newDataList;
        notifyDataSetChanged();
    }
}

