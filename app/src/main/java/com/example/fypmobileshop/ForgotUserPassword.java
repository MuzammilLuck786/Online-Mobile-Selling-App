package com.example.fypmobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotUserPassword extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_user_password);

        TextInputEditText emailreset=findViewById(R.id.emailreset);
        Button resetpassword=findViewById(R.id.resetpassword);
        ProgressBar forgotbar = findViewById(R.id.forgotbar);

        mAuth=FirebaseAuth.getInstance();


        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email1=emailreset.getText().toString().trim();
                if (email1.isEmpty()){
                    emailreset.setError("Email is required");
                    emailreset.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                   emailreset.setError("Valid Email is Required");
                   emailreset.requestFocus();
                   return;
                }
                else{
                    forgotbar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotUserPassword.this, "Check Your Email to Reset Your Password", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Userlogin.class));
                                forgotbar.setVisibility(View.GONE);
                            }
                            else {
                                Toast.makeText(ForgotUserPassword.this, "Try again! Something went Wrong", Toast.LENGTH_SHORT).show();
                                forgotbar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

}