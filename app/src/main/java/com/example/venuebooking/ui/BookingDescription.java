package com.example.venuebooking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.venuebooking.R;

public class BookingDescription extends AppCompatActivity {
    TextView back,eventtitle,venuename,slot,payment,cost,userid,name,count,food,cleaning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_description_layout);

        eventtitle=findViewById(R.id.evnttitle);
        venuename=findViewById(R.id.venuename);
        slot=findViewById(R.id.slot);
        payment=findViewById(R.id.payment);
        cost=findViewById(R.id.cost);
        userid=findViewById(R.id.userid);
        name=findViewById(R.id.username);
        count=findViewById(R.id.count);
        food=findViewById(R.id.food);
        cleaning=findViewById(R.id.cleaning);

        Intent intent=getIntent();
        eventtitle.setText(intent.getStringExtra("eventtitle"));
        venuename.setText(intent.getStringExtra("venuename"));
        slot.setText(intent.getStringExtra("slot"));
        payment.setText(intent.getStringExtra("payment"));
        cost.setText(intent.getStringExtra("cost"));
        userid.setText(intent.getStringExtra("uid"));
        name.setText(intent.getStringExtra("name"));
        count.setText(intent.getStringExtra("count"));
        food.setText(intent.getStringExtra("food"));
        cleaning.setText(intent.getStringExtra("cleaing"));

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}