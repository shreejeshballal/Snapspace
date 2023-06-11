package com.example.venuebooking.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.venuebooking.R;


public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        CardView whtsapp=view.findViewById(R.id.whtsapp);
        CardView gmail=view.findViewById(R.id.mail);
        CardView phone=view.findViewById(R.id.phone);
        CardView location=view.findViewById(R.id.location);
        whtsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wurl="https://wa.me/+917349693447?text=hiii";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wurl));
                startActivity(intent);
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipient = "sudeep2002naik@gmail.com";
                String subject = "Hello";
                String body = "This is the body of the email.";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "7349693447";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = 12.866773099716804;
                double longitude = 74.92538832883592;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:" + latitude + "," + longitude));
                startActivity(intent);
            }
        });
        return view;
    }
}