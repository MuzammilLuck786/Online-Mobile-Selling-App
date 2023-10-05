package com.example.fypmobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
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

public class Userlogin extends AppCompatActivity {

    TextInputEditText emailuser,passworduser;
    Button btnloginuser;
    TextView usersignup;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        emailuser=findViewById(R.id.emailuser);
        passworduser=findViewById(R.id.passworduser);
        btnloginuser=findViewById(R.id.btnloginuser);
        usersignup=findViewById(R.id.signupBtnuser);
        TextView forgotpassword=findViewById(R.id.forgetpassword);
        ProgressBar userprogressbar = findViewById(R.id.userprogressbar);


        mAuth=FirebaseAuth.getInstance();

        usersignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserRegistration.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotUserPassword.class));
            }
        });


        btnloginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = emailuser.getText().toString().trim();
                String password = passworduser.getText().toString().trim();

                if (email.isEmpty()){
                    emailuser.setError("Email is required");
                    emailuser.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    passworduser.setError("Password is required");
                    passworduser.requestFocus();
                    return;
                }
                if (password.length() !=6){
                    passworduser.setError("Please enter a correct 6 digit password");
                    passworduser.requestFocus();
                    return;
                }
                else {
                    userprogressbar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()){
                                Toast.makeText(Userlogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                userprogressbar.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                finish();

                            }
                            else {
                                Toast.makeText(Userlogin.this, "Check Your Email and Verify Your Account First", Toast.LENGTH_SHORT).show();
                                userprogressbar.setVisibility(View.GONE);

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Userlogin.this,e.getMessage(),Toast.LENGTH_LONG).show();


                        }
                    });
                }

            }
        });
}
}