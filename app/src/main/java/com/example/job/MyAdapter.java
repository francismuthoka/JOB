package com.example.job;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    FirebaseDatabase firebaseDatabase;
    String user_name;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    Context context;
    ArrayList<MyData> myDataArrayList;

    public MyAdapter(Context context, ArrayList<MyData> myDataArrayList) {
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference=firebaseDatabase.getReference("users");
        this.context = context;
        this.myDataArrayList = myDataArrayList;

        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name=snapshot.child("first name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        MyData myData=myDataArrayList.get(position);
        holder.job_tittle_tv.setText(myData.getJob_tittle());
        holder.job_location_tv.setText(myData.getJob_location());
        holder.job_salary_tv.setText(myData.getJob_salary());
        holder.job_vacancies_tv.setText(myData.getJob_vacancies());
        holder.due_date_tv.setText(myData.getSelect_due_date());

        String job_name=holder.job_tittle_tv.getText().toString();

        if (user_name.equals("admin")){
            holder.more_details.setText("applicants");
            holder.more_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view =new View(context);
                    view.getContext();
                    Intent intent=new Intent(context,Applicants.class);
                    intent.putExtra("",job_name);
                    view.getContext().startActivity(intent);
                }
            });
        }
        else {
            holder.more_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view =new View(context);
                    view.getContext();
                    Intent intent=new Intent(context,ApplyJob.class);
                    intent.putExtra("",job_name);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myDataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView job_tittle_tv,job_location_tv,job_salary_tv,job_vacancies_tv,due_date_tv,more_details;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            job_tittle_tv=itemView.findViewById(R.id.job_tittle_tv);
            job_location_tv=itemView.findViewById(R.id.job_location_tv);
            job_salary_tv=itemView.findViewById(R.id.job_salary_tv);
            job_vacancies_tv=itemView.findViewById(R.id.job_vacancies_tv);
            due_date_tv=itemView.findViewById(R.id.due_date_tv);
            more_details=itemView.findViewById(R.id.more_details);
        }
    }
}
