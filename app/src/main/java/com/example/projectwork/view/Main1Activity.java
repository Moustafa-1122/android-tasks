package com.example.projectwork.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.projectwork.R;
import com.google.android.material.navigation.NavigationView;

public class Main1Activity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private String currentScreen = "TIMER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new TasksFragment())
                    .commit();

            navigationView.setCheckedItem(R.id.homeId);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                //  Logout
                if (itemId == R.id.logoutId) {
                    performLogout();
                    return true;
                }
                Fragment selectedFragment = null;
                String title = "";


                if (itemId == R.id.homeId) {
                    selectedFragment = new TasksFragment();
                    title = "Home"; // ⭐ العنوان
                } else if (itemId == R.id.profileId) {
                    selectedFragment = new ProfileFragment();
                    title = "Profile"; // ⭐ العنوان
                } else if (itemId == R.id.settingsId) {
                    selectedFragment = new SettingsFragment();
                    title = "Settings"; // ⭐ العنوان
                } else if (itemId == R.id.aboutId) {
                    selectedFragment = new TimerFragment();
                    title = "About"; // ⭐ العنوان
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    setToolbarTitle(title); // Change the toolbar title
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

    }


    // LogOut Function
    private void performLogout() {
        // إنشاء Dialog للتأكيد
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logou?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    Toast.makeText(this, "Logout clicked!", Toast.LENGTH_LONG).show();

                    try {
                        // Delete the SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Toast.makeText(this, "SharedPrefs cleared", Toast.LENGTH_SHORT).show();

                        // to Login page
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Cancels the dialog
                .setCancelable(true)
                .show();
    }

    private void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}