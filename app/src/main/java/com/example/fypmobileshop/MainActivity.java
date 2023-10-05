package com.example.fypmobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to Return?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("No",null)
                .show();
    }
    public void logout(){
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Toast.makeText(MainActivity.this, "Successfully Logged out", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialog.show();
    }


    RecyclerView recycler_view1;

    FirebaseFirestore db;


    MobileAdapter mobileAdapter;


    SearchView searchView;

    List<MobileModel> mobileModelList = new ArrayList<>();

    FloatingActionButton mycart;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView aboutUs = findViewById(R.id.AboutUs);

        ImageView Logout = findViewById(R.id.Logout);
        searchView = findViewById(R.id.Searchview);






        mycart = findViewById(R.id.mycart);

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContactUs.class));
            }
        });

        mycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), My_Cart.class));
            }
        });


        ProgressBar Progressbarmain = findViewById(R.id.Progressbarmain);
        Progressbarmain.setVisibility(View.VISIBLE);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        recycler_view1 = findViewById(R.id.recycler_view1);
        db = FirebaseFirestore.getInstance();

        recycler_view1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        mobileModelList = new ArrayList<>();
        mobileAdapter = new MobileAdapter(getApplicationContext(), mobileModelList);
        recycler_view1.setAdapter(mobileAdapter);

        db.collection("MobileLists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MobileModel mobileModel = document.toObject(MobileModel.class);
                                mobileModelList.add(mobileModel);
                                mobileAdapter.notifyDataSetChanged();
                                Progressbarmain.setVisibility(View.GONE);

                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            Progressbarmain.setVisibility(View.GONE);
                        }
                    }
                });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String Phonename) {
                if (Phonename.isEmpty()){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
//                    mobileModelList.clear();
//                    mobileAdapter.notifyDataSetChanged();
                }
                else {
                    SearchMobile(Phonename);
                }
                return true;
            }
        });


    }

    private void SearchMobile(String Phonename) {
        if (!Phonename.isEmpty()){
            db.collection("MobileLists").orderBy("Phonename").startAt(Phonename).endAt(Phonename +"\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mobileModelList.clear();
                                mobileAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    MobileModel mobileModel = doc.toObject(MobileModel.class);
                                    mobileModelList.add(mobileModel);
                                    recycler_view1.setAdapter(mobileAdapter);
                                    mobileAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

