package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.redbox.medicare.UtilsService.UtilService;

public class PatientRegister extends AppCompatActivity {

    private EditText name,mobile,password,age;
    private Button register;
    private TextView signIn;
    private Spinner gender;
    CharSequence[] GenderType;
    UtilService utilService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        mobile = findViewById(R.id.mobileNumber);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        signIn = findViewById(R.id.signin);
        gender = findViewById(R.id.gender);
        utilService = new UtilService();

        GenderType = getResources().getStringArray(R.array.Gender_Type);
        ArrayAdapter genderType = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, GenderType);
        genderType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderType);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientRegister.this, LoginActivity.class));
            }
        });
    }
}