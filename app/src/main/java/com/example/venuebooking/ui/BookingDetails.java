package com.example.venuebooking.ui;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class BookingDetails extends AppCompatActivity {
    ArrayList <String> allSlots = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String venue_id,date,date_id;
    List<Integer> array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Intent intent = getIntent();
        venue_id=intent.getStringExtra("id");
        date = intent.getStringExtra("date");
        date_id = intent.getStringExtra("date_id");
        Toast.makeText(BookingDetails.this, date_id + venue_id, Toast.LENGTH_SHORT).show();
        allSlots.add("6-8");
        allSlots.add("8-10");
        allSlots.add("10-12");
        allSlots.add("12-14");
        allSlots.add("14-16");


        DocumentReference mainDocRef = db.collection("venues").document(venue_id);
        DocumentReference subcollectionDocRef = mainDocRef.collection("date").document(date_id);

        subcollectionDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    Toast.makeText(BookingDetails.this, "Failed", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Toast.makeText(BookingDetails.this, "retrieved", Toast.LENGTH_SHORT).show();
                    array = (List<Integer>) snapshot.get("slots");
                    Toast.makeText(BookingDetails.this, "array", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(BookingDetails.this, date_id+venue_id, Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Current data: null");
                }
            }
        });
        //fixSlots();

    }

    private void fixSlots() {

            for (int j = 0; j< Objects.requireNonNull(array).size(); j++)
            {


                    allSlots.remove(array.get(j));

            }
        }

}