package com.example.venuebooking.ui;

import android.content.DialogInterface;
import androidx.annotation.NonNull;

import com.example.venuebooking.R;
import com.example.venuebooking.fragments.BookingFragment;
import com.example.venuebooking.fragments.ContactFragment;
import com.example.venuebooking.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class BottomNavigator extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeuser);
        frameLayout=(FrameLayout)findViewById(R.id.framelayout);
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationView);


        getSupportFragmentManager().beginTransaction().add(R.id.framelayout,new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new HomeFragment()).commit();
                        break;
                    case R.id.register:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new BookingFragment()).commit();
                        break;
                    case R.id.contact:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new ContactFragment()).commit();
                        break;
                    case R.id.logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(BottomNavigator.this);
                        View logoutModal = getLayoutInflater().inflate(R.layout.logout_modal, null);
                        builder.setView(logoutModal);

                        Button logoutButton = logoutModal.findViewById(R.id.logout_button);
                        Button cancelButton = logoutModal.findViewById(R.id.cancel_button);

                        AlertDialog dialog = builder.create();
                        dialog.show();

                        logoutButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mAuth.signOut();

                                finish();
                            }
                        });

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        return true;



                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BottomNavigator.this);
        View logoutModal = getLayoutInflater().inflate(R.layout.logout_modal, null);
        builder.setView(logoutModal);

        Button logoutButton = logoutModal.findViewById(R.id.logout_button);
        Button cancelButton = logoutModal.findViewById(R.id.cancel_button);

        AlertDialog dialog = builder.create();
        dialog.show();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}