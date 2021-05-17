package com.redbox.medicare.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.redbox.medicare.R;

import java.util.ArrayList;


public class PatientFragment extends Fragment {
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> arrayAdapter_gender;
    CharSequence[] gender;



    public PatientFragment() {
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
        View view = inflater.inflate(R.layout.fragment_patient, container, false);
        textInputLayout = view.findViewById(R.id.gender);
        autoCompleteTextView = view.findViewById(R.id.genderList);
        gender = view.getResources().getStringArray(R.array.Gender_Type);
        arrayAdapter_gender = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,gender);
        autoCompleteTextView.setAdapter(arrayAdapter_gender);
        autoCompleteTextView.setThreshold(1);
        return view;
    }
}