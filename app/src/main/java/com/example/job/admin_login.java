package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin_login extends AppCompatActivity {

    Button admin_login_btn;
    EditText admin_username,admin_password;

    DatabaseReference dbreference;
    FirebaseDatabase db;
    FirebaseAuth auth;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        dbreference=db.getReference();

        admin_username=findViewById(R.id.admin_username);
        admin_login_btn=findViewById(R.id.admin_login_btn);
        admin_password=findViewById(R.id.admin_password);

        admin_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=admin_username.getText().toString().trim();
                String Password=admin_password.getText().toString().trim();

                if(Name.isEmpty()){
                    admin_username.setError("please enter your admin password");
                    admin_username.requestFocus();
                }
                else if (Password.isEmpty()){
                    admin_password.setError("please enter your admin username");
                    admin_password.requestFocus();
                }
                else {
                    progressDialog=new ProgressDialog(admin_login.this);
                    progressDialog.setTitle("login admin");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("please wait...");
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(Name,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(admin_login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(admin_login.this,AdminDashbaord.class));
                                finish();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(admin_login.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }

}