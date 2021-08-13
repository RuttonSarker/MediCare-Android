package com.redbox.medicare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private TextView name,department,visit;
    private TextView patientName,patientMobile,patientAge;
    private DatePickerDialog datePickerDialog;
    private EditText availableDateE;
    private Button payment;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> arrayAdapter;
    CharSequence[] timeslot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        availableDateE = findViewById(R.id.date);
        textInputLayout = findViewById(R.id.schedule);
        autoCompleteTextView = findViewById(R.id.scheduleList);

        Intent dep = getIntent();
        name = findViewById(R.id.name);

        String nameE = dep.getStringExtra("username");
        name.setText(nameE);

        department = findViewById(R.id.department);
        String departmentE = dep.getStringExtra("department");

        department.setText(departmentE);
        visit = findViewById(R.id.visit);

        String visitE = dep.getStringExtra("visit");
        visit.setText(visitE);


        //Timeslot
        timeslot = getResources().getStringArray(R.array.Appointment_Time);
        arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,timeslot);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);


        availableDateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                availableDateE.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        payment = findViewById(R.id.pay);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
                startActivity(new Intent(MainActivity.this,SslCommerzPaymentGateway.class));
            }

            private void AddUser() {

            }
        });

        }

    }
