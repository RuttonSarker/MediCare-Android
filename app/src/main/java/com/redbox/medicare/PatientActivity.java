package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatientActivity extends AppCompatActivity {

    private CardView doctor,appointment,prescription,report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        doctor = findViewById(R.id.doctor);
        appointment = findViewById(R.id.appointment);
        prescription = findViewById(R.id.prescription);
        report = findViewById(R.id.medicalReport);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientActivity.this,DoctorList.class));
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientActivity.this,PatientAppointment.class));
            }
        });

        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientActivity.this,PatientPrescription.class));
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientActivity.this,PatientMedicalReport.class));
            }
        });


    }
}