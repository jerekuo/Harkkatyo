package com.example.moovi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Database {
    private static Database instance = new Database();
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
        myRef.child("users").child(String.valueOf(u.getUserId())).setValue(u);
    }

    public void addRoom(Room r) {
        DatabaseReference myRef = database.getReference();
        myRef.child("rooms").child(String.valueOf(r.getRoomId())).setValue(r);
    }
}
