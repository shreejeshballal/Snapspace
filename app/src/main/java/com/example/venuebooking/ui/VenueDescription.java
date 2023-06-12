package com.example.venuebooking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.venuebooking.R;

public class VenueDescription extends AppCompatActivity {
    ImageView venue_img;
    TextView venue_name,venue_descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_booking);
        Intent intent = getIntent();
        String name = intent.getStringExtra("venue_name");
        String desc = intent.getStringExtra("venue_desc");
        String image = intent.getStringExtra("venue_img");

        venue_name = findViewById(R.id.venue_name);
        venue_descr = findViewById(R.id.venue_desc);
        venue_img = findViewById(R.id.venue_image);

        venue_name.setText(name);
        venue_descr.setText(desc);
        Glide.with(this).load(image).into(venue_img);


    }
}