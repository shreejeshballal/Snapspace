package com.example.venuebooking.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public static String fb_user_id;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView not_registered,forgot_password;
    Button login;
    TextInputEditText editEmail,editPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        mAuth = FirebaseAuth.getInstance();

        //Mapping
        forgot_password = findViewById(R.id.forgotpassword);
        not_registered = findViewById(R.id.notregistered);
        login = findViewById(R.id.login_btn);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);

        //-------Functions-----------

        //Login functionality
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email, password;
                email = String.valueOf(editEmail.getText());
                password = String.valueOf(editPassword.getText());
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please enter an email ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        fb_user_id = user.getUid();
                                        Intent intent = new Intent(LoginActivity.this, BottomNavigator.class);
                                        startActivity(intent);
                                        Toast.makeText(LoginActivity.this, "Login " +
                                                        "successful",
                                                Toast.LENGTH_SHORT).show();

                                        editEmail.setText("");
                                        editPassword.setText("");

                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    try {
                                        throw task.getException();
                                    } catch (Exception e) {
                                        editEmail.setText("");
                                        editPassword.setText("");
                                        Toast.makeText(LoginActivity.this, e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
             
            }
        });

        //To go back to signup activity
        not_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //Forgot password functionality
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.forgotpassword,null);
                FirebaseAuth auth = FirebaseAuth.getInstance();

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {

                    TextInputEditText forgotEmail;
                    String email;
                    @Override
                    public void onClick(View v){
                        forgotEmail = dialogView.findViewById(R.id.forgotEmail);
                        email = String.valueOf(forgotEmail.getText());

                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(LoginActivity.this, "Please enter an email ID", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Email sent successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            forgotEmail.setText("");
                                            dialog.dismiss();

                                        }else{
                                            try {
                                                throw task.getException();
                                            } catch (Exception e) {
                                                forgotEmail.setText("");
                                                Toast.makeText(LoginActivity.this, e.getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                    }
                });

                dialogView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        dialog.dismiss();
                    }
                });
            dialog.show();
            }
        });

    }
    /*public void onBackPressed() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this);
        View logoutModal = getLayoutInflater().inflate(R.layout.logout_modal, null);
        builder.setView(logoutModal);

        Button logoutButton = logoutModal.findViewById(R.id.logout_button);
        Button cancelButton = logoutModal.findViewById(R.id.cancel_button);

        androidx.appcompat.app.AlertDialog dialog = builder.create();
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

    }*/

    }

