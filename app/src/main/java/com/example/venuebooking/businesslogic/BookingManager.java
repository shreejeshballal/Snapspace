package com.example.venuebooking.businesslogic;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.venuebooking.R;
import com.example.venuebooking.models.BookingModel;
import com.example.venuebooking.ui.BookingDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class BookingManager {
    Context context;

    static public ArrayList<String> availableSlots = new ArrayList<String>();
    static public ArrayList<String> bookedSlots = new ArrayList<String>();
    static public ArrayList<String> allSlots = new ArrayList<String>();
    DocumentReference dateDocumentRef,bookingDocumentRef;

    public BookingManager(DocumentReference dateDocumentRef, DocumentReference bookingDocumentRef, Context context) {
        this.dateDocumentRef = dateDocumentRef;
        this.context = context;
        this.bookingDocumentRef = bookingDocumentRef;

    }

    public void bookSlots(String venueId,String slotToBeBooked,String name,String cost,String phone,String date,String title,String userId,String userCount,boolean cleaning,boolean food,String venueName) {

        bookingDocumentRef.set(new BookingModel(name,cost,date,slotToBeBooked,title,userId,userCount,venueId,cleaning,food,false,false,venueName,phone)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                                showBookingStatus(context,"Booking successful!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                                showBookingStatus(context,"Error booking. Please try again later!");

                    }
                });


        dateDocumentRef.update("slots", FieldValue.arrayUnion(slotToBeBooked)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w("FirebaseSlotRetriever", "Success.");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FirebaseSlotRetriever", "Success.", e);

                    }
                });



    }

    static public void reAdjustSlots()
    {

        if(bookedSlots!=null) {
            availableSlots.clear();
            availableSlots.addAll(allSlots);
            availableSlots.removeAll(bookedSlots);
        }
        else{
            availableSlots.clear();
            availableSlots.addAll(allSlots);
        }


    }
      public void showBookingStatus(Context context, String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
}

    public ArrayList<String> getAvailableSlots() {
        return availableSlots;
    }


}
