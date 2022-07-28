package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Applicants extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);
        listView=findViewById(R.id.listView);
        firebaseDatabase=FirebaseDatabase.getInstance();
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.applicants_item,R.id.first_name,arrayList);

        Intent intent=getIntent();
        String job_name=intent.getStringExtra("");
        databaseReference=firebaseDatabase.getReference("uploadPDF");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    PutPdf putPdf=dataSnapshot.getValue(PutPdf.class);
                    String tempjobname=putPdf.getJob_name().toString();
                    if (job_name.equals(tempjobname)){
                            arrayList.add(putPdf.getApplicant_name().toString());
                    }
                    else {

                    }
                }
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}