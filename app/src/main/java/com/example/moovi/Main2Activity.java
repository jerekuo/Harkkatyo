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

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//THIS ACTIVITY IS MAIN MENU FOR THE APP

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FirebaseUser user;
    Database database = Database.getInstance();
    User currentUser;
    final ArrayList<Reservation> reservations = new ArrayList<>();

    //Sets the current user to hallsystem for easy access to user information when needed.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.getInstance().getUserFromDB(new OnGetDataListener() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
                HallSystem.getInstance().setUseri(currentUser);
                System.out.println("USERI SETATTU");

                if (currentUser == null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Fragment fragment;
                    fragment = new SettingsFragment();
                    transaction.replace(R.id.fragmentView,fragment);
                    transaction.commit();
                }
            }

            @Override
            public void onSuccess(@NonNull Task<QuerySnapshot> task) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

        setContentView(R.layout.activity_main2);
        //writes the users reservations to a list in hallsystem for easy access later.
        database.writeReservationList();

        //Gets user object from hallsystem singleton.

        user = HallSystem.getInstance().getUser();
        toolbar = findViewById(R.id.main_toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        //Writes Halllist so we can fill spinners with the information later.
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






    }

    @Override
    protected void onStart(){
        super.onStart();
        //writes users own reservations to list so we can populate the home screen later.
        database.writeCurrentUserReservationList(user.getEmail(), new OnGetDataListener() {



            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }

            @Override
            public void onSuccess(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentsnapshot : task.getResult()){

                    String[] tokens = documentsnapshot.getId().split("[,]");


                    if (tokens[0].equalsIgnoreCase(user.getEmail())){

                        reservations.add(documentsnapshot.toObject(Reservation.class));
                    } else {

                    }



                }
                HallSystem.getInstance().setCurUserResList(reservations);
            }



            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

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
            writeReservationlist();
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
        //Opens home Fragment if pressed
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        fragment = new HomeFragment();
        transaction.replace(R.id.fragmentView, fragment);
        transaction.commit();
    }

    //method writes a list with current users reservations, used for displaying them to users
    public void writeReservationlist(){
        database.writeCurrentUserReservationList(user.getEmail(), new OnGetDataListener() {



            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }

            @Override
            public void onSuccess(@NonNull Task<QuerySnapshot> task) {
                reservations.clear();
                for (QueryDocumentSnapshot documentsnapshot : task.getResult()){

                    String[] tokens = documentsnapshot.getId().split("[,]");


                    if (tokens[0].equalsIgnoreCase(user.getEmail())){

                        reservations.add(documentsnapshot.toObject(Reservation.class));
                    } else {

                    }



                }
                HallSystem.getInstance().setCurUserResList(reservations);
            }



            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

}

