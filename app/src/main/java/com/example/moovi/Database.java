package com.example.moovi;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;



public class Database {
    HallSystem hallsystem = HallSystem.getInstance();
    private static Database instance = new Database();
    FirebaseUser user = HallSystem.getInstance().getUser();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Hall> halls;
    ArrayList<Room> rooms;



    private Database() {
        //null constructor
    }

    public static Database getInstance() {
        return instance;
    }

    public void addHall(Hall h) {

    }

    public void addUser(User u) {
        Map<String, Object> userO = new HashMap<>();
        userO.put("firstName", u.getFirstName());
        userO.put("lastName", u.getLastName());
        userO.put("email", user.getEmail());
        userO.put("password", u.getPassword());
        userO.put("userId", user.getUid());

        db.collection("Users").document(user.getEmail())
                .set(userO)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Success", "DocumentSnapshot success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error", "error writing document", e);
                    }
                });

    }

    public void addRoom(Room r) {
        }

    public void getUserFromDB(String id) {



    }
    public void writeHallList(){
        halls = new ArrayList<>();
        db.collection("AllHalls")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int i=0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getData().get("name").toString();
                                String address = document.getData().get("address").toString();
                                ArrayList<Room> list = writeRoomList(document.getId());

                                halls.add(new Hall(i,name,address,list));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                i++;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Setting halls to hallList
        hallsystem.setHallList(halls);
    }

    public ArrayList<Room> writeRoomList(String hall){
        rooms = new ArrayList<>();
        db.collection("AllHalls").document(hall).collection("Rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int i = 0;


                for (QueryDocumentSnapshot documentsnapshot : task.getResult()){
                    i++;
                    String name = documentsnapshot.getData().get("name").toString();
                    int cap = Integer.parseInt(documentsnapshot.getData().get("capacity").toString());
                    System.out.println(name);
                    System.out.println(cap);
                    System.out.println(i);
                    rooms.add(new Room(name,cap,"Kaikki pelaa",i));
                    Log.d(TAG, documentsnapshot.getId() + " => " + documentsnapshot.getData());
                }
            }
        });
        for (Room r : rooms){
            System.out.println("@@@@@@@@@@@@"+r.name+"@@@@@@@@@@@@");
        }
        return rooms;
    }




}
