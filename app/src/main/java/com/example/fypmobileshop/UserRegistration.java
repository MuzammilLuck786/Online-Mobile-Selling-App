package com.example.fypmobileshop;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        TextInputEditText useremail = findViewById(R.id.useremail);
        TextInputEditText userpassword = findViewById(R.id.userpassword);
        TextInputEditText userpasswordretype = findViewById(R.id.userpasswordretype);
        Button usersignupbtn = findViewById(R.id.usersignupbtn);
        TextView loginUser = findViewById(R.id.loginuser);
        ProgressBar Registrationbar= findViewById(R.id.Registrationbar);

        mAuth = FirebaseAuth.getInstance();

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Userlogin.class));


            }
        });

        usersignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email=useremail.getText().toString().trim();
                String password=userpassword.getText().toString().trim();
                String password2=userpasswordretype.getText().toString().trim();

                if (email.isEmpty()){
                    useremail.setError("Email is Required");
                    useremail.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    userpassword.setError("Password is Required");
                    userpassword.requestFocus();
                    return;
                }
                if (password.length() !=6 ){
                    userpassword.setError("Password Must Be 6 characters");
                    userpassword.requestFocus();
                    return;

                }
                if (!password2.equals(password)){
                    userpasswordretype.setError("The password is not matching with above password");
                    userpasswordretype.requestFocus();
                    return;

                }
                else {
                    Registrationbar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                user.sendEmailVerification();
                                Toast.makeText(UserRegistration.this, "Check Your Email to Verify Your Account", Toast.LENGTH_LONG).show();
                                Registrationbar.setVisibility(View.GONE);

                            }
                            else {
                                Toast.makeText(UserRegistration.this, "Registration Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Registrationbar.setVisibility(View.GONE);

                            }

                        }
                    });

                }

            }
        });

    }
}