package com.example.venuebooking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.venuebooking.R;

public class DateSlotPicker extends AppCompatActivity {
    String venue_name,date;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_slot_picker);
        Intent intent = getIntent();
        venue_name = intent.getStringExtra("venue_name");
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
        Intent intent = new Intent(this, BookingDetails.class);
        intent.putExtra("venue_name",venue_name);
        intent.putExtra("date",date);
        startActivity(intent);

    }
}