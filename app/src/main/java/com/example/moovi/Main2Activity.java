package com.example.moovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FirebaseUser user;
    Database database = Database.getInstance();
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.getInstance().getUserFromDB();

        setContentView(R.layout.activity_main2);
        database.writeReservationList();


        user = HallSystem.getInstance().getUser();
        toolbar = findViewById(R.id.main_toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        database.writeHallList();


        setSupportActionBar(toolbar);
        Toast.makeText(Main2Activity.this, "Logged in as: " + user.getEmail(),
                Toast.LENGTH_SHORT).show();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

       /* currentUser = HallSystem.getInstance().getUseri();
f
        if (currentUser == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment;
            fragment = new SettingsFragment();
            transaction.replace(R.id.fragmentView,fragment);
            transaction.commit();
        }*/



    }

    @Override
    protected void onStart(){
        super.onStart();
        database.writeCurrentUserReservationList(user.getEmail());
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        if (item.getTitle().toString().equalsIgnoreCase("settings")) {
            fragment = new SettingsFragment();
            transaction.replace(R.id.fragmentView,fragment);

        } else if (item.getTitle().toString().equalsIgnoreCase("Reservations")) {
            fragment = new ReservationFragment();
            HallSystem hallSystem = HallSystem.getInstance();
            final ArrayList<Hall> list = hallSystem.getHallList();
            transaction.replace(R.id.fragmentView, fragment);
        }
        else if (item.getTitle().toString().equalsIgnoreCase("Calendar")){
            fragment = new Calendar_Fragment();
            transaction.replace(R.id.fragmentView, fragment);

        } else if (item.getTitle().toString().equalsIgnoreCase("log out")) {
            Toast.makeText(Main2Activity.this, "Logged out!",
                    Toast.LENGTH_SHORT).show();
            HallSystem.getInstance().setUser(null);
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
        } else if (item.getTitle().toString().equalsIgnoreCase("Home")) {
            //database.writeCurrentUserReservationList(user.getEmail());
            fragment = new HomeFragment();
            transaction.replace(R.id.fragmentView, fragment);
        }


        drawerLayout.closeDrawers();
        transaction.commit();
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        //Only accepts going to login screen via logout button.
    }

}

