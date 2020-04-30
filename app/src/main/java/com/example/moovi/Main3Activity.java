package com.example.moovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FirebaseUser user;
    Database database = Database.getInstance();
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.getInstance().getUserFromDB(new OnGetDataListener() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
                HallSystem.getInstance().setUseri(currentUser);
                System.out.println("USERI SETATTU");

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
        database.writeRoomList("Huhtari");
        database.writeRoomList("Sammonlahden urheiluhalli");
        database.writeRoomList("Urheilutalo");
        setContentView(R.layout.activity_main3);

        user = HallSystem.getInstance().getUser();
        toolbar = findViewById(R.id.main_toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        database.writeHallList();

        setSupportActionBar(toolbar);
        Toast.makeText(Main3Activity.this, "Logged in as: " + user.getEmail(),
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        if (item.getTitle().toString().equalsIgnoreCase("users")) {
            fragment = new EditUserInfoFragment();
            transaction.replace(R.id.fragmentView,fragment);

        }else if (item.getTitle().toString().equalsIgnoreCase("reservations")){
            fragment = new EditReservationsFragment();
            transaction.replace(R.id.fragmentView,fragment);

        }
        transaction.commit();
        return false;
    }
}
