package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplyJob extends AppCompatActivity {

    Button apply_btn;
    TextView tittle_tv,location_tv,salary_tv,vacancies_tv,date_tv,company_name_tv,company_email_tv,company_address_tv,fq_tv,sq_tv,thq_tv,ftq_tv,fth_tv;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    String job_tittle;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);
        apply_btn=findViewById(R.id.apply_btn);
        tittle_tv=findViewById(R.id.tittle_tv);
        location_tv=findViewById(R.id.location_tv);
        salary_tv=findViewById(R.id.salary_tv);
        vacancies_tv=findViewById(R.id.vacancies_tv);
        date_tv=findViewById(R.id.date_tv);
        company_name_tv=findViewById(R.id.company_name_tv);
        company_email_tv=findViewById(R.id.company_email_tv);
        company_address_tv=findViewById(R.id.company_address_tv);
        fq_tv=findViewById(R.id.first_qual_tv);
        sq_tv=findViewById(R.id.second_qual_tv);
        thq_tv=findViewById(R.id.third_qual_tv);
        ftq_tv=findViewById(R.id.fourth_qual_tv);
        fth_tv=findViewById(R.id.fifth_qual_tv);

        Intent intent=getIntent();
        job_tittle=intent.getStringExtra("");

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("jobs").child(job_tittle);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                job_tittle=snapshot.child("job_tittle").getValue().toString();
                tittle_tv.setText(job_tittle);
                location_tv.setText(snapshot.child("job_location").getValue().toString());
                salary_tv.setText(snapshot.child("job_salary").getValue().toString());
                vacancies_tv.setText(snapshot.child("job_vacancies").getValue().toString());
                date_tv.setText(snapshot.child("select_due_date").getValue().toString());
                company_name_tv.setText(snapshot.child("company_name").getValue().toString());
                company_email_tv.setText(snapshot.child("company_email").getValue().toString());
                company_address_tv.setText(snapshot.child("company_postal").getValue().toString());
                fq_tv.setText("1. "+snapshot.child("first_qualification").getValue().toString());
                sq_tv.setText("2. "+snapshot.child("second_qualification").getValue().toString());
                thq_tv.setText("3. "+snapshot.child("third_qualification").getValue().toString());
                fth_tv.setText("4. "+snapshot.child("fourth_qualification").getValue().toString());
                ftq_tv.setText("5. "+snapshot.child("fifth_qualification").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference=firebaseDatabase.getReference("users");
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.child("first name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference=firebaseDatabase.getReference().child("uploadPDF");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    PutPdf putPdf=dataSnapshot.getValue(PutPdf.class);

                    String tempName=putPdf.getApplicant_name().toString();
                    String tempTittle=putPdf.getJob_name().toString();

                    if (user.equals(tempName)&&job_tittle.equals(tempTittle)){
                        apply_btn.setEnabled(false);
                    }
                    else {

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ApplyJob.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle=tittle_tv.getText().toString();
                Intent intent2=new Intent(ApplyJob.this,Upload_cv.class);
                intent2.putExtra("",tittle);
                startActivity(intent2);
            }
        });


    }
}