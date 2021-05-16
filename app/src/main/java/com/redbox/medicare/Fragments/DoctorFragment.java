package com.redbox.medicare.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.redbox.medicare.R;

public class DoctorFragment extends Fragment {

    TextInputLayout textInputLayout_gender,textInputLayout_department;
    AutoCompleteTextView autoCompleteTextView_gender,autoCompleteTextView_department;
    private EditText name,mobile,password,bmdc,degree,medical,fees;
    private Button button;

    ArrayAdapter<String> arrayAdapter_gender;
    ArrayAdapter<String> arrayAdapter_department;
    CharSequence[] gender;
    CharSequence[] department;


    public DoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        textInputLayout_gender = view.findViewById(R.id.gender);
        textInputLayout_department = view.findViewById(R.id.department);
        name = view.findViewById(R.id.name);
        mobile = view.findViewById(R.id.mobileNumber);
        password = view.findViewById(R.id.password);
        bmdc = view.findViewById(R.id.bmdcID);
        fees = view.findViewById(R.id.visit);
        degree = view.findViewById(R.id.degree);
        button = view.findViewById(R.id.register);

        autoCompleteTextView_gender = view.findViewById(R.id.genderList);
        autoCompleteTextView_department = view.findViewById(R.id.departmentList);

        //Gender
        gender = view.getResources().getStringArray(R.array.Gender_Type);
        arrayAdapter_gender = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, gender);
        autoCompleteTextView_gender.setAdapter(arrayAdapter_gender);
        autoCompleteTextView_gender.setThreshold(1);

        //Department
        department = view.getResources().getStringArray(R.array.Department_List);
        arrayAdapter_department = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, department);
        autoCompleteTextView_department.setAdapter(arrayAdapter_department);
        autoCompleteTextView_department.setThreshold(1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
            }
        });

        return view;

    }

}