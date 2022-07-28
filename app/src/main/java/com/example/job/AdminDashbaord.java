package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDashbaord extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    final static String myname="mutua";

    public String name;

    CardView card_home,card_logout,card_applicants,card_add_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashbaord);
        card_home =findViewById(R.id.home);
        card_add_job=findViewById(R.id.add_job);
        card_applicants=findViewById(R.id.applicants);
        card_logout=findViewById(R.id.logout);
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        databaseReference=firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name=snapshot.child("first name").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });

        card_applicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashbaord.this,main_dashboad.class);
                intent.putExtra("",name);
                startActivity(intent);
            }
        });

        card_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if (firebaseUser!=null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(AdminDashbaord.this,admin_login.class));
                    finish();
                }
                else {
                    Toast.makeText(AdminDashbaord.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        card_add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashbaord.this,add_job.class));
            }
        });

    }
}