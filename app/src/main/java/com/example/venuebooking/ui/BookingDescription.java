package com.example.venuebooking.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookingDescription extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String venueId,dateId,documentId,Plainslot;
    TextView back, eventTitle,venueName,slot,payment,cost, userId, userName,count,food,cleaning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_description_layout);
        eventTitle =findViewById(R.id.bookingEventTitle);
        venueName=findViewById(R.id.bookingVenueName);
        slot=findViewById(R.id.bookingVenueSlot);
        payment=findViewById(R.id.bookingPaymentStatus);
        cost=findViewById(R.id.bookingEventCost);
        userId =findViewById(R.id.bookingUserId);
        userName =findViewById(R.id.bookingUserName);
        count=findViewById(R.id.bookingUserCount);
        food=findViewById(R.id.bookingFood);
        cleaning=findViewById(R.id.bookingCleaning);
        Intent intent=getIntent();

         boolean status,paymentBool;

         status = intent.getBooleanExtra("status1",false);
        LinearLayout linearLayout;
        linearLayout = findViewById(R.id.booking_desc_main);


        if(!status)
        {
            linearLayout.setBackgroundResource(R.drawable.booking_description_red_bg);

        }
       /* if(status && paymentBool)
        {
            Log.d("statusHello",String.valueOf(status));
            Log.d("paymentBool",String.valueOf(status));
            Log.d("chdck",String.valueOf(status));

            linearLayout.setBackgroundResource(R.drawable.booking_green_bg);
        } else if(!status && !paymentBool)                              
        {                                    Log.d("chdck",String.valueOf(status));

            linearLayout.setBackgroundResource(R.drawable.booking_description_red_bg);
        }                                                   
        else if(status && !paymentBool){
            Log.d("chdck",String.valueOf(status));
            Log.d("chdck",String.valueOf(status));
            Log.d("chdck",String.valueOf(status));

            linearLayout.setBackgroundResource(R.drawable.booking_description_purple_bg);
        }   */





        String slotInfo =  intent.getStringExtra("slot")+" IST";
        String venueCost  = "₹ "+intent.getStringExtra("cost") ;
        Plainslot = intent.getStringExtra("slot");
        venueId = intent.getStringExtra("venueId");
        dateId = intent.getStringExtra("dateId");

        documentId = intent.getStringExtra("documentId");

        eventTitle.setText(intent.getStringExtra("eventtitle"));
        venueName.setText(intent.getStringExtra("venueName"));
        slot.setText(slotInfo);
        payment.setText(intent.getStringExtra("payment"));
        cost.setText(venueCost);
        userId.setText(intent.getStringExtra("uid"));
        userName.setText(intent.getStringExtra("name"));
        count.setText(intent.getStringExtra("count"));
        food.setText(intent.getStringExtra("food"));
        cleaning.setText(intent.getStringExtra("cleaning"));

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void payBooking(View view)  {
                                  finish();  
    }
    public void cancelBooking(View view) {
        DocumentReference mainDocRef = db.collection("venues").document(venueId);
        DocumentReference dateDocumentRef = mainDocRef.collection("date").document(dateId);
        DocumentReference bookingDocumentRef = db.collection("bookings").document(documentId);


        dateDocumentRef.update("slot", FieldValue.arrayRemove(Plainslot));
        bookingDocumentRef
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BookingDescription.this, "Booking cancelled",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookingDescription.this, "Error cancelling!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}