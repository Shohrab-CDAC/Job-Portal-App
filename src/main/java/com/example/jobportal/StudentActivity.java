package com.example.jobportal;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class StudentActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeAllJobFragment homeAllJobFragment = new HomeAllJobFragment();
    AppliedJobFragment appliedJobFragment = new AppliedJobFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    SavedJobsFragment savedJobsFragment = new SavedJobsFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up the initial badge for the notification item
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notification);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(8);

        // Set up the listener for bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.alljobhome) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeAllJobFragment).commit();
                    return true;
                } else if (itemId == R.id.notification) {
                    // Clear the badge when notification item is selected
                    badgeDrawable.clearNumber();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
                    return true;
                } else if (itemId == R.id.appliedjob) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, appliedJobFragment).commit();
                    return true;
                } else if (itemId == R.id.savedjobs) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, savedJobsFragment).commit();
                    return true;
                } else if (itemId == R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
