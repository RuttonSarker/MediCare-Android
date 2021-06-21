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

import com.google.android.material.tabs.TabLayout;
import com.redbox.medicare.Fragments.DailyReportFragment;
import com.redbox.medicare.Fragments.TestReportFragment;

import java.util.ArrayList;
import java.util.List;


public class PatientPrescription extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DailyReportFragment dailyReportFragment;
    private TestReportFragment testReportFragment;
    private Toolbar mytoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription);

        mytoolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mytoolbar);

        viewPager = findViewById(R.id.viewPage);
        tabLayout = findViewById(R.id.tabLayout);

        dailyReportFragment = new DailyReportFragment();
        testReportFragment = new TestReportFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(dailyReportFragment,"Daily Report");
        viewPagerAdapter.addFragment(testReportFragment,"Medical Test");
        viewPager.setAdapter(viewPagerAdapter);
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
