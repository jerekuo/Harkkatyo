package com.example.moovi;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Database {
    private static Database instance = new Database();
    FirebaseUser user = HallSystem.getInstance().getUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();



    private Database() {
        //null constructor
    }

    public static Database getInstance() {
        return instance;
    }

    public void addHall(Hall h) {
        DatabaseReference myRef = database.getReference();
        myRef.child("halls").child(String.valueOf(h.getHallId())).setValue(h);
    }

    public void addUser(User u) {
        DatabaseReference myRef = database.getReference();
        myRef.child("users").child(user.getUid()).setValue(u);
        Backup.getInstance().writeUserBackup(u);
    }

    public void addRoom(Room r) {
        DatabaseReference myRef = database.getReference();
        myRef.child("rooms").child(String.valueOf(r.getRoomId())).setValue(r);
    }

    public void getUser(String id) {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ERROR", "loadPost:onCancelled", databaseError.toException());
            }

        };


    }
}
