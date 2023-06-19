package com.example.admin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.Models.BookingModel;
import com.example.admin.R;
import com.example.admin.adapters.Booking_Adapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;


public class Reservation extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<BookingModel> bookingModelList = new ArrayList<>();
    RecyclerView bookingRecyler;
    Booking_Adapter bookingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        bookingRecyler = view.findViewById(R.id.bookingrecycler);
        bookingRecyler.setLayoutManager(new LinearLayoutManager(requireContext()));
        bookingAdapter = new Booking_Adapter(requireContext(),bookingModelList,bookingRecyler);
        bookingRecyler.setAdapter(bookingAdapter);

        db.collection("bookings")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<BookingModel> newBookingList = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                // Retrieve data from the document
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
                                boolean status = Boolean.TRUE.equals(document.getBoolean("status"));
                                String venueName = document.getString("venueName");

                                BookingModel bookingModel = new BookingModel(name, cost, date, slot, title, userId, userCnt, venueId, cleaning, food, payment, status, venueName, phone);
                                newBookingList.add(bookingModel);
                            }

                            // Update the adapter with the new data
                            bookingAdapter.setData(newBookingList);
                        }
                    } else {
                        Log.d("bookingDebug", "Error getting documents: ", task.getException());
                    }
                });




        return view;
    }
}