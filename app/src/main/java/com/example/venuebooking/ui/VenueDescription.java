package com.example.venuebooking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.venuebooking.R;

public class VenueDescription extends AppCompatActivity {

    ImageView venue_img;
    TextView venue_name,venue_descr;
    String name,desc,image,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_description);

        //Mapping the respective views to variables initialized
        venue_name = findViewById(R.id.venue_name);
        venue_descr = findViewById(R.id.venue_desc);
        venue_img = findViewById(R.id.venue_image);
        //Getting venue details from the recycler view items
        Intent intent = getIntent();
         name = intent.getStringExtra("venue_name");
         id = intent.getStringExtra("venue_id");
         desc = intent.getStringExtra("venue_desc");
         image = intent.getStringExtra("venue_img");
        //Setting the content of the views as the received data
        venue_name.setText(name);
        venue_descr.setText(desc);
        Glide.with(this).load(image).into(venue_img);


    }


    //Function to proceed to the data and slot picking
    public void bookNow(View view) {




        Intent intent = new Intent(this, DateSlotPicker.class);
        intent.putExtra("venue_id",id);
        startActivity(intent);
    }

    public void goBack(View view) {
        finish();
    }
}