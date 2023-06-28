package com.example.venuebooking.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class BookingDescription extends AppCompatActivity implements PaymentResultListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String venueId,dateId,documentId,Plainslot,realCost;
    Button payBtn;
    TextView back, eventTitle,venueName,slot,payment,cost, userId, userName,count,food,cleaning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();

        Checkout.preload(getApplicationContext());
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
        payBtn = findViewById(R.id.bookingPayButton);

        boolean status,paymentBool;
        paymentBool = intent.getBooleanExtra("paymentBool",false);
        status = intent.getBooleanExtra("status1",false);
        LinearLayout linearLayout;
        linearLayout = findViewById(R.id.booking_desc_main);


        if(!status)
        {
            payBtn.setEnabled(false);
            linearLayout.setBackgroundResource(R.drawable.booking_description_red_bg);
        }
         if(paymentBool){
             payBtn.setEnabled(false);

         }


        


        realCost = intent.getStringExtra("cost");
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
        PaymentNow(realCost);

    }

    private void PaymentNow(String amount) {
        Intent intent=getIntent();

        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_rrBDVz90iIzSrl");
        checkout.setImage(R.mipmap.ic_launcher);
        double finalAmount  = Float.parseFloat(amount)*100;
        try{
            JSONObject options = new JSONObject();
            options.put("name",intent.getStringExtra("name"));
            options.put("description",documentId);
            options.put("theme.color","#7528B8");
            options.put("currency","INR");
            options.put("amount",finalAmount+"");
            checkout.open(activity,options);
        }  catch (Exception e)
        {
            Toast.makeText(this,"Error paying",Toast.LENGTH_SHORT).show();

        }

        
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

    @Override
    public void onPaymentSuccess(String s) {
        DocumentReference bookingDocumentRef = db.collection("bookings").document(documentId);
        bookingDocumentRef.update("payment",true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Payment Success",Toast.LENGTH_SHORT).show();
            }
        })     .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Please contact the admin",Toast.LENGTH_SHORT).show();

            }
        });

        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),"Payment unsuccessful",Toast.LENGTH_SHORT).show();


    }
}