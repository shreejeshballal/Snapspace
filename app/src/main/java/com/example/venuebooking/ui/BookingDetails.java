package com.example.venuebooking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.example.venuebooking.businesslogic.BookingManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Objects;

import javax.annotation.Nullable;

public class BookingDetails extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextInputEditText txt_date,txt_user_name,txt_event_name,txt_user_count,txt_user_phone;
    CheckBox check_food,check_cleaning;
    Button booking_btn;
    BookingManager bookingManager;
    String venue_id, date, date_id,slot,phone,name,title,usercount,cost="0",baseFee="200",venue_name;
    Boolean cleaning,food;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        //Intent data
        Intent intent = getIntent();
        venue_id = intent.getStringExtra("id");
        date = intent.getStringExtra("date");
        date_id = intent.getStringExtra("date_id");
        venue_name = intent.getStringExtra("venueName");




        //Mapping and configuring
        spinner = findViewById(R.id.spinner);
        txt_date =findViewById(R.id.date);
        txt_event_name = findViewById(R.id.event_name);
        txt_user_count = findViewById(R.id.count);
        txt_user_name  = findViewById(R.id.user_name);
        txt_user_phone = findViewById(R.id.phoneNo);
        check_cleaning = findViewById(R.id.cleaning);
        booking_btn = findViewById(R.id.booking_btn);
        check_food = findViewById(R.id.food);
        txt_user_phone.setKeyListener(DigitsKeyListener.getInstance("0123456789-+() "));
        txt_user_count.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        txt_date.setText(date);
        BookingManager.allSlots.clear();
        BookingManager.allSlots.add("Select a slot");
        BookingManager.allSlots.add("6:00 - 8:00");
        BookingManager. allSlots.add("8:00 - 10:00");
        BookingManager.allSlots.add("10:00 - 12:00");
        BookingManager.allSlots.add("12:00 - 14:00");
        BookingManager.allSlots.add("14:00 - 16:00");
        BookingManager.allSlots.add("16:00 - 18:00");
        BookingManager.allSlots.add("18:00 - 20:00");


        DocumentReference mainDocRef = db.collection("venues").document(venue_id);
        DocumentReference dateDocumentRef = mainDocRef.collection("date").document(date_id);
        DocumentReference bookingDocumentRef = db.collection("bookings").document();
        bookingManager =    new BookingManager(dateDocumentRef,bookingDocumentRef,this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dateDocumentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("FirebaseSlotRetriever", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.w("FirebaseSlotRetriever", "Success.", e);
                   BookingManager.bookedSlots = (ArrayList<String>) snapshot.get("slots");
                    BookingManager.reAdjustSlots();
                    adapter.clear();
                    adapter.addAll(bookingManager.getAvailableSlots());
                    adapter.notifyDataSetChanged();
                    if(String.valueOf(BookingManager.availableSlots.size()).equals("1"))
                    {
                        Toast.makeText(BookingDetails.this, "No time slot available", Toast.LENGTH_SHORT).show();
                        booking_btn.setEnabled(false);
                    }

                } else {
                    Log.d("FirebaseSlotRetriever", "Current data: null");
                }
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                slot = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                slot = parent.getItemAtPosition(0).toString();
            }
        });

    }


    public void request(View view) {

        String fbuser = LoginActivity.fb_user_id;
        name = Objects.requireNonNull(txt_user_name.getText()).toString();
        title = Objects.requireNonNull(Objects.requireNonNull(txt_event_name.getText())).toString();
        usercount = Objects.requireNonNull(txt_user_count.getText()).toString();
        cleaning  = check_cleaning.isChecked();
        food  = check_food.isChecked();
        phone = Objects.requireNonNull(txt_user_phone.getText()).toString();




        if(name.isEmpty() || title.isEmpty() || usercount.isEmpty())
        {
            Toast.makeText(BookingDetails.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        if(slot.equals("Select a slot")){
            Toast.makeText(BookingDetails.this, "Please select a slot", Toast.LENGTH_SHORT).show();
            return;
         }

       if(cleaning)
           cost = "200";
       if(food)
           cost = Integer.toString((Integer.parseInt(cost)+65*Integer.parseInt(usercount)));
        cost = Integer.toString((Integer.parseInt(cost)+Integer.parseInt(baseFee)));

       bookingManager.bookSlots(venue_id,slot,name,cost,phone,date,title,fbuser,usercount,cleaning,food,venue_name,date_id);
        finish();
    }

    public void backButton(View view) {
        finish();
        BookingManager.allSlots.clear();
    }
}