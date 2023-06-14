package com.example.venuebooking.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateSlotPicker extends AppCompatActivity {
    String venue_id,date,date_id;
    DatePicker datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_slot_picker);
        Intent intent = getIntent();
        venue_id = intent.getStringExtra("venue_id");
        datePicker =findViewById(R.id.date_picker);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                         date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                    }
                });
    }

    public void goBack(View view) {
        finish();
    }

    public void Proceed(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("venues")
                .document(venue_id)
                .collection("date").whereEqualTo("date",date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    date_id = document.getId();
                                }

                                Toast.makeText(DateSlotPicker.this, "Proceeding...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DateSlotPicker.this, BookingDetails.class);
                                intent.putExtra("id",venue_id);
                                intent.putExtra("date",date);
                                intent.putExtra("date_id",date_id);
                                startActivity(intent);


                            } else {

                                if(date!=null) {
                                    createDatecollect();
                                    Toast.makeText(DateSlotPicker.this, "Setting up the date...", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(DateSlotPicker.this, "Select the date again!", Toast.LENGTH_SHORT).show();

                                }

                            }
                        } else {
                            // An error occurred while retrieving the documents
                            Toast.makeText(DateSlotPicker.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    private void createDatecollect() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("venues");
        String documentId = venue_id;
        String subcollectionName = "date";


        DocumentReference documentRef = collectionRef.document(documentId);

// Create the subcollection
        CollectionReference subcollectionRef = documentRef.collection(subcollectionName);

// Add a document to the subcollection
        Map<String, Object> data = new HashMap<>();
        data.put("date", date);
        data.put("slots", Collections.singletonList(null));
        subcollectionRef.add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Document added successfully
                        String date_id = documentReference.getId();
                        Toast.makeText(DateSlotPicker.this, "Added the date", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DateSlotPicker.this, BookingDetails.class);
                        intent.putExtra("id",venue_id);
                        intent.putExtra("date",date);
                        intent.putExtra("date_id",date_id);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error occurred while adding the document
                        Log.w("TAG", "Error adding document", e);
                        Toast.makeText(DateSlotPicker.this, "Error! Try again.", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}