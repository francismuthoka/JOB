package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BlendMode;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class main_dashboad extends AppCompatActivity {

    ArrayList<MyData> myDataArrayList;
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    TextView user_name_tv;
    TextView message_tv;
    ImageView image_id;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboad);


        Intent intent=getIntent();
        name=intent.getStringExtra("");


        user_name_tv=findViewById(R.id.user_name_tv);
        image_id=findViewById(R.id.image_id);


        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myDataArrayList=new ArrayList<MyData>();
        myAdapter=new MyAdapter(this,myDataArrayList);

        message_tv=findViewById(R.id.message_tv);

        recyclerView.setAdapter(myAdapter);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference=firebaseDatabase.getReference("jobs");

        if (name.equals("admin")){
            user_name_tv.setText(null);
            image_id.setVisibility(View.INVISIBLE);
            message_tv.setText("WELCOME ADMIN");
        }
        else {
            user_name_tv.setText(name);
            Calendar calendar=Calendar.getInstance();

            if(calendar.get(Calendar.HOUR_OF_DAY)>0&&calendar.get(Calendar.HOUR_OF_DAY)<12)
            {
                message_tv.setText("good morning. welcome to job app");
            }
            else if(calendar.get(Calendar.HOUR_OF_DAY)>=12&&calendar.get(Calendar.HOUR_OF_DAY)<=17)
            {
                message_tv.setText("good afternoon. welcome to job app");
            }
            else
            {
                message_tv.setText("good evening. welcome to job app");
            }

        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyData myData=dataSnapshot.getValue(MyData.class);
                    myDataArrayList.add(myData);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(main_dashboad.this,"error occurred",Toast.LENGTH_SHORT).show();
            }
        });

    }

}