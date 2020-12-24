package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoctorActivity extends AppCompatActivity {

    private CardView schedule,appointment,prescription,report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        schedule = findViewById(R.id.schedule);
        appointment = findViewById(R.id.appointment);
        prescription = findViewById(R.id.prescription);
        report = findViewById(R.id.medicalReport);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorActivity.this,ScheduleManager.class));
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorActivity.this,AppointmentList.class));
            }
        });

        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorActivity.this,PrescriptionRecord.class));
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorActivity.this,MedicalReportList.class));
            }
        });

    }
}