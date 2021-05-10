package com.redbox.medicare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.redbox.medicare.Fragments.DailyReportFragment;
import com.redbox.medicare.Fragments.DoctorFragment;
import com.redbox.medicare.Fragments.PatientFragment;
import com.redbox.medicare.Fragments.TestReportFragment;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DoctorFragment doctorFragment;
    private PatientFragment patientFragment;
    private Toolbar mytoolbar;
    private TextView toolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medical_report);

        mytoolbar = findViewById(R.id.toolBar);
       // toolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        viewPager = findViewById(R.id.viewPage);
        tabLayout = findViewById(R.id.tabLayout);

        doctorFragment = new DoctorFragment();
        patientFragment = new PatientFragment();

        tabLayout.setupWithViewPager(viewPager);

        RegisterActivity.ViewPagerAdapter viewPagerAdapter = new RegisterActivity.ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(patientFragment,"Patient");
        viewPagerAdapter.addFragment(doctorFragment,"Doctor");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_user_male);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_doctor_profile);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){

            fragments.add(fragment);
            fragmentTitle.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}