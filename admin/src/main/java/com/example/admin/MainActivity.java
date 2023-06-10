package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.admin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout=(FrameLayout)findViewById(R.id.framelayout);
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationView);
    }
}