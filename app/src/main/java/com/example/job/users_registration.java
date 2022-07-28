package com.example.job;

import java.lang.reflect.Member;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class users_registration extends AppCompatActivity {
    TextInputEditText phone_no,id_number,f_name,l_name,password,email;
    EditText select_date;
    DatePickerDialog.OnDateSetListener onDatesetListener;
    RadioGroup gender_rd_grp;
    RadioButton male_rd_btn,female_rd_btn;
    ProgressDialog progressDialog;
    Button register_btn;
    FirebaseDatabase db;
    DatabaseReference dbreference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_registration);
        select_date=findViewById(R.id.select_date);
        male_rd_btn=findViewById(R.id.male_rd_btn);
        female_rd_btn=findViewById(R.id.female_rd_btn);
        gender_rd_grp=findViewById(R.id.gender_rd_grp);
        phone_no=findViewById(R.id.phone_no);
        id_number=findViewById(R.id.id_number);
        f_name=findViewById(R.id.f_name);
        l_name=findViewById(R.id.l_name);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        register_btn=findViewById(R.id.register_btn);
        db=FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();
        dbreference=db.getReference("users");



        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(users_registration.this,onDatesetListener,year,month,day);
                datePickerDialog.show();
            }
        });
        onDatesetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                select_date.setText(date);
            }
        };

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String f_name_text=f_name.getText().toString().trim();
        String l_name_text=l_name.getText().toString().trim();
        String email_text=email.getText().toString().trim();
        String password_text=password.getText().toString().trim();
        String id_no= id_number.getText().toString().trim();
        String  phone_no_text= phone_no.getText().toString().trim();
        String select_date_Text=select_date.getText().toString().trim();
        String gender=null;


        if(!male_rd_btn.isChecked()&&!female_rd_btn.isChecked()){
            male_rd_btn.setError("please choose your gender");
            male_rd_btn.requestFocus();
            female_rd_btn.setError("please choose your gender");
            female_rd_btn.requestFocus();
        }

        else if(male_rd_btn.isChecked()){
            gender="male";
        }

        else if(female_rd_btn.isChecked()){
            gender="female";
        }

        if(f_name_text.isEmpty())
        {
            f_name.setError("this can not be empty");
            f_name.requestFocus();
        }
        else if(l_name_text.isEmpty())
        {
            l_name.setError("this can not be empty");
            l_name.requestFocus();
        }
        else if(id_no.length()<7||id_no.length()>8)
        {
            id_number.setError("invalid id number");
            id_number.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email_text).matches())
        {
            email.setError("invalid email");
            email.requestFocus();
        }
        else if (phone_no_text.length()<10||phone_no_text.length()>12)
        {
            phone_no.setError("invalid id number");
            phone_no.requestFocus();
        }
        else if (true!=passwordValidates(password_text)){
            password.setError("enter strong password");
            password.requestFocus();
        }
        else if (select_date_Text.isEmpty()){
            select_date.setError("please enter your date of birth");
            select_date.requestFocus();
        }
        else {
            String finalGender = gender;
            progressDialog = new ProgressDialog(users_registration.this);
            progressDialog.setTitle("registering");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("please wait...");
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email_text,password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            dbreference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("date of birth").setValue(select_date_Text);
                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("email").setValue(email_text);
                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("password").setValue(password_text);
                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("phone").setValue(phone_no_text);
                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("id no").setValue(id_no);
                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("gender").setValue(finalGender);
                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("first name").setValue(f_name_text);
                                        dbreference.child(auth.getCurrentUser().getUid().toString().trim()).child("last name").setValue(l_name_text);
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "successfull", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(users_registration.this, MainActivity.class));
                                        finish();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }

    }

    private boolean passwordValidates( String pass ) {
        int count = 0;
        if( 8 <= pass.length() && pass.length() <= 32  )
        {
            if(pass.matches(".*\\d.*"))
                count ++;
            if(pass.matches(".*[a-z].*"))
                count ++;
            if(pass.matches(".*[A-Z].*"))
                count ++;
        }
        return count>=3;
    }
}