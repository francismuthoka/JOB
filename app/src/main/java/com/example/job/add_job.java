package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class add_job extends AppCompatActivity {

   EditText job_tittle,job_location,job_salary,select_due_date,job_vacancies,
            company_name,company_email,company_postal,
            first_qualification,second_qualification,third_qualification,fourth_qualification,fifth_qualification;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Button job_btn;
    ProgressDialog progressDialog;
    DatePickerDialog.OnDateSetListener onDatesetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        job_tittle=findViewById(R.id.job_tittle);
        job_location=findViewById(R.id.job_location);
        job_salary=findViewById(R.id.job_salary);
        job_vacancies=findViewById(R.id.job_vacancies);
        company_name=findViewById(R.id.company_name);
        company_email=findViewById(R.id.company_address);
        company_postal=findViewById(R.id.company_postalAddress);
        first_qualification=findViewById(R.id.first_qualification);
        second_qualification=findViewById(R.id.second_qualification);
        third_qualification=findViewById(R.id.third_qualification);
        fourth_qualification=findViewById(R.id.fourth_qualification);
        fifth_qualification=findViewById(R.id.fifth_qualification);
        firebaseDatabase=FirebaseDatabase.getInstance();
        select_due_date=findViewById(R.id.select_due_date);
        databaseReference=firebaseDatabase.getReference("jobs");
        job_btn=findViewById(R.id.job_btn);

        select_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(add_job.this,onDatesetListener,year,month,day);
                datePickerDialog.show();
            }
        });
        onDatesetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month=month+1;
                Calendar calendar=Calendar.getInstance();
                String date=dayOfMonth+"/"+month+"/"+year;
                select_due_date.setText(date);
            }
        };

        job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJob();
            }
        });

    }

    private void addJob() {
        String job_tittle_text=job_tittle.getText().toString().trim();
        String job_location_text=job_location.getText().toString().trim();
        String job_salary_text=job_salary.getText().toString().trim();
        String job_vacancies_text=job_vacancies.getText().toString().trim();
        String select_due_date_text=select_due_date.getText().toString().trim();

        String company_name_text=company_name.getText().toString().trim();
        String company_email_text=company_email.getText().toString().trim();
        String company_postal_text=company_postal.getText().toString().trim();

        String first_text=first_qualification.getText().toString().trim();
        String second_text=second_qualification.getText().toString().trim();
        String third_text=third_qualification.getText().toString().trim();
        String fourth_text= fourth_qualification.getText().toString().trim();
        String fifth_text=fifth_qualification.getText().toString().trim();

        if(job_tittle_text.isEmpty())
        {
            job_tittle.setError("this field can not be empty");
            job_tittle.requestFocus();
        }

        else if(job_location_text.isEmpty())
        {
            job_location.setError("this field can not be empty");
            job_location.requestFocus();
        }

        else if(job_salary_text.isEmpty())
        {
            job_salary.setError("this field can not be empty");
            job_salary.requestFocus();
        }

        else if(select_due_date_text.isEmpty())
        {
            select_due_date.setError("please choose due date");
            select_due_date.requestFocus();
        }

        else if(job_vacancies_text.isEmpty())
        {
            job_vacancies.setError("this field can not be empty");
            job_vacancies.requestFocus();
        }

        else if(company_name_text.isEmpty())
        {
            company_name.setError("this field can not be empty");
            company_name.requestFocus();
        }

        else if(company_email_text.isEmpty())
        {
            company_email.setError("this field can not be empty");
            company_email.requestFocus();
        }

        else if(company_postal_text.isEmpty())
        {
            company_postal.setError("this field can not be empty");
            company_postal.requestFocus();
        }

        else if(first_text.isEmpty())
        {
            fifth_qualification.setError("this field can not be empty");
            first_qualification.requestFocus();
        }

        else if(second_text.isEmpty())
        {
            second_qualification.setError("this field can not be empty");
            second_qualification.requestFocus();
        }

        else if(third_text.isEmpty())
        {
            third_qualification.setError("this field can not be empty");
            third_qualification.requestFocus();
        }

        else {
            progressDialog=new ProgressDialog(add_job.this);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setTitle("adding job");
            progressDialog.setMessage("please wait...");
            JobClass jobClass=new JobClass(job_tittle_text,job_location_text,job_salary_text,job_vacancies_text,select_due_date_text,company_name_text,company_email_text,company_postal_text,first_text,second_text,third_text,fourth_text,fifth_text);
            progressDialog.show();

            databaseReference.child(job_tittle_text).setValue(jobClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(add_job.this, "Job Added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),AdminDashbaord.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(add_job.this, " failed to add a job "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}