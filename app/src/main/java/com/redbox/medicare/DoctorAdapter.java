package com.redbox.medicare;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    ArrayList<Doctor>arrayList;

    Context context;

    public DoctorAdapter(Context context, ArrayList<Doctor> arrayList) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_doctor_list,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.MyViewHolder holder, int position) {

        final Doctor doctor = arrayList.get(position);
        final String id = arrayList.get(position).getId();
        final String name = arrayList.get(position).getName();
        final String degree = arrayList.get(position).getDegree();
        final String department = arrayList.get(position).getDepartment();
        final String medical = arrayList.get(position).getMedical();
        final String visit = arrayList.get(position).getVisit();

        holder.id.setText(id);
        holder.name.setText(name);
        holder.degree.setText(degree);
        holder.department.setText(department);
        holder.medical.setText(medical);
        holder.visit.setText(visit);
        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("_id",doctor.getId());
                intent.putExtra("username",doctor.getName());
                intent.putExtra("department",doctor.getDepartment());
                intent.putExtra("degree",doctor.getDegree());
                intent.putExtra("medical",doctor.getMedical());
                intent.putExtra("visit",doctor.getVisit());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id,name,degree,department,medical,visit;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            degree = itemView.findViewById(R.id.degree);
            department = itemView.findViewById(R.id.department);
            medical = itemView.findViewById(R.id.medical);
            visit = itemView.findViewById(R.id.visit);
            button = itemView.findViewById(R.id.appointment);
        }
    }
}