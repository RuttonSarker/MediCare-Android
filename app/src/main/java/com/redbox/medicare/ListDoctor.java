package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListDoctor extends AppCompatActivity {

    private CardView general,corona,ophthalmology,orthopedics,cardiology,neurology,nutritionist,medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);
        general = findViewById(R.id.general);
        corona = findViewById(R.id.corona);
        ophthalmology = findViewById(R.id.ophthalmology);
        orthopedics = findViewById(R.id.orthopedics);
        cardiology = findViewById(R.id.cardiology);
        neurology = findViewById(R.id.neurology);
        nutritionist = findViewById(R.id.nutritionist);
        medicine = findViewById(R.id.medicine);

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "general";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

        corona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "corona";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

        cardiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "cardiology";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

        neurology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "neurology";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

        ophthalmology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "ophthalmology";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

        orthopedics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "orthopedics";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "medicine";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

        nutritionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String department = "nutritionist";
                Intent dep = new Intent(ListDoctor.this,FindDoctor.class);
                dep.putExtra("department",department);
                startActivity(dep);
            }
        });

    }
}