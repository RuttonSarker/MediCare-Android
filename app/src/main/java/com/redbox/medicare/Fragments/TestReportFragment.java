package com.redbox.medicare.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.redbox.medicare.R;

import java.util.Calendar;


public class TestReportFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> arrayAdapter;
    CharSequence[] medicalTest;



    public TestReportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_report, container, false);

        floatingActionButton = view.findViewById(R.id.addButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog();

            }
        });

        return view;
    }

    private void showAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_medical_report_layout, null);
        textInputLayout = alertLayout.findViewById(R.id.test);
        autoCompleteTextView = alertLayout.findViewById(R.id.MedicalTestList);

        //medical Test
        medicalTest = alertLayout.getResources().getStringArray(R.array.MedicalTest_List);
        arrayAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,medicalTest);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);

        final EditText date = alertLayout.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getLayoutInflater().getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(alertLayout)
                .setTitle("")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }
}