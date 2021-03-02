package com.redbox.medicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.redbox.medicare.UtilsService.SharedPreference;

public class PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private CardView doctor,appointment,prescription,report;
    ImageView menuTop;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout contentView;
    static final float END_SCALE = 0.7f;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        doctor = findViewById(R.id.doctor);
        appointment = findViewById(R.id.appointment);
        prescription = findViewById(R.id.prescription);
        report = findViewById(R.id.medicalReport);
        drawerLayout = findViewById(R.id.drawer_patient_layout);
        navigationView = findViewById(R.id.navigation_patient);
        menuTop = findViewById(R.id.menuTop);
        contentView = findViewById(R.id.contentView);
        sharedPreference = new SharedPreference(this);

        navigationDrawer();

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientActivity.this,ListDoctor.class));
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

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        menuTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);

                else drawerLayout.openDrawer(GravityCompat.START);
            }

        });
        animateNavigationDrawer();
        MyMenu();
    }

    private void MyMenu() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.profile) {
                    startActivity(new Intent(PatientActivity.this,PatientProfile.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else if (id == R.id.notification) {
                    startActivity(new Intent(PatientActivity.this,PatientNotification.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else if (id == R.id.messege) {
                    startActivity(new Intent(PatientActivity.this,PatientMessege.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else if (id == R.id.feedback) {
                    startActivity(new Intent(PatientActivity.this,Feedback.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else if (id == R.id.about) {
                    startActivity(new Intent(PatientActivity.this,AboutUs.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else if (id == R.id.logout){
                    sharedPreference.clear();
                    startActivity(new Intent(PatientActivity.this,LoginActivity.class));
                    finish();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }

                return false;
            }

        });
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        } else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}