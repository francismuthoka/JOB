package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextInputEditText email, password;
    Button login_btn,reset_btn,register_btn;
    FirebaseDatabase db;
    DatabaseReference dbreference;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login_btn=findViewById(R.id.login_btn);
        reset_btn=findViewById(R.id.reset_btn);
        register_btn=findViewById(R.id.register_btn);
        db=FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();
        dbreference=db.getReference();
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,users_registration.class));
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }


    private void loginUser() {
        String emailText=email.getText().toString().trim();
        String passwordText=password.getText().toString().trim();
        if(emailText.isEmpty()){
            email.setError("enter valid email address");
            email.requestFocus();
       }
        else if(passwordText.isEmpty()){
            password.setError("enter strong password");
            password.requestFocus();

        }
        else{
             progressDialog=new ProgressDialog(MainActivity.this);
             progressDialog.setTitle("login");
             progressDialog.setMessage("please wait...");
             progressDialog.setCanceledOnTouchOutside(false);
             progressDialog.show();
             auth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                           progressDialog.dismiss();
                           Toast.makeText(getApplicationContext(), "successful", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(MainActivity.this, UserDashboard.class));
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