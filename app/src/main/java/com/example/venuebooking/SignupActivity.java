package com.example.venuebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //Variable Initialisations
    TextView already_registered;
    Button signup;
    TextInputEditText editEmail,editPassword,editConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);


        //Mapping
        already_registered=findViewById(R.id.alredyregistered);
        signup = findViewById(R.id.register_btn);
        editPassword = findViewById(R.id.password);
        editEmail = findViewById(R.id.email);
        editConfirmPassword = findViewById(R.id.confirmpassword);

        //-------------Functions---------------------

        //To register new user
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email,password,confirmPassword;
                email = String.valueOf(editEmail.getText());
                password = String.valueOf(editPassword.getText());
                confirmPassword = String.valueOf(editConfirmPassword.getText());

                //Check for any of the fields are empty, if empty display toast message
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignupActivity.this,"Please enter an email ID",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignupActivity.this,"Please enter a password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(SignupActivity.this,"Please enter your password again",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking if password matches the confirm password and then if true registering the user into our app and going to log in page else display error message
                if(password.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // If success, take the user to the login page

                                        Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(SignupActivity.this, "Registeration " +
                                                        "successful",
                                                Toast.LENGTH_SHORT).show();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        try{
                                            throw task.getException();

                                        }catch(Exception e)
                                        {
                                            Toast.makeText(SignupActivity.this, e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(SignupActivity.this, "Password does not match!.",
                            Toast.LENGTH_SHORT).show();


                }
            }
        });

        //To go back to signin activity
        already_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}