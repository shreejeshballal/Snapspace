package com.example.venuebooking.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.example.venuebooking.adapters.BookingAdapter;
import com.example.venuebooking.models.BookingModel;
import com.example.venuebooking.ui.LoginActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;



public class BookingFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<BookingModel> bookingModelList = new ArrayList<>();
  RecyclerView bookingRecyler;
    BookingAdapter bookingAdapter;
    //shashank
    String documentId;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);
        bookingRecyler = view.findViewById(R.id.booking_recycler);
        bookingRecyler.setLayoutManager(new LinearLayoutManager(requireContext()));
        bookingAdapter = new BookingAdapter(requireContext(),bookingModelList,bookingRecyler);
        bookingRecyler.setAdapter(bookingAdapter);



        Log.d("bookingDebug", "Constructor");



        db.collection("bookings")
                .whereEqualTo("userId", LoginActivity.fb_user_id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        List<BookingModel> newBookingList = new ArrayList<>();
                        for (DocumentSnapshot document : Objects.requireNonNull(value).getDocuments()) {

                            //shashank
                            //String documentId = document.getId();
                            String name = document.getString("name");
                            String cost = document.getString("cost");
                            String phone = document.getString("phone");
                            String date = document.getString("date");
                            String venueId = document.getString("venueId");
                            String slot = document.getString("slot");
                            String title = document.getString("title");
                            String userId = document.getString("userId");
                            String userCnt = document.getString("userCnt");
                            boolean cleaning = Boolean.TRUE.equals(document.getBoolean("cleaning"));
                            boolean food = Boolean.TRUE.equals(document.getBoolean("food"));
                            boolean payment = Boolean.TRUE.equals(document.getBoolean("payment"));
                            Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
                            boolean status = Boolean.TRUE.equals(document.getBoolean("status"));
                            String venueName = document.getString("venueName");

                            BookingModel bookingModel = new BookingModel(name,cost,date,slot,title,userId,userCnt,venueId,cleaning,food,payment,status,venueName,phone);
                            newBookingList.add(bookingModel);
                            Log.d("bookingDebug", "Set data method reached"+bookingModel);
                            Log.d("bookingDebug", "list address"+newBookingList);


                        }
                        Log.d("bookingDebug", "list address"+newBookingList);

                        Toast.makeText(getActivity(),String.valueOf(newBookingList.size()),Toast.LENGTH_SHORT).show();
                        Log.d("bookingDebug", "Set data method reached");

                        bookingAdapter.setData(newBookingList);
                    }
                });
        return view;
    }
}