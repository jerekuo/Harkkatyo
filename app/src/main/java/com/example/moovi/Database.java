package com.example.moovi;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    //Adds users information to database
    public void addUser(User u) {

        db.collection("Users").document(user.getEmail())
                .set(u)
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

    //Method for getting users details from db for currently logged in user
    public User getUserFromDB() {
        final User[] useri = {new User()};
        DocumentReference docRef = db.collection("Users").document(user.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                useri[0] = documentSnapshot.toObject(User.class);

            }
        });

        return useri[0];
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
                                halls.add(new Hall(i,name,address));
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

    public void writeRoomList(Hall h){
        String hall = h.getHallName();
        db.collection("AllHalls").document(hall).collection("Rooms").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int i = 0;
                rooms = new ArrayList<>();

                for (QueryDocumentSnapshot documentsnapshot : queryDocumentSnapshots){
                    i++;
                    String name = documentsnapshot.getData().get("name").toString();
                    int cap = Integer.parseInt(documentsnapshot.getData().get("capacity").toString());
                    System.out.println(name);
                    System.out.println(cap);
                    System.out.println(i);
                    rooms.add(new Room(name,cap,"Kaikki pelaa",i));
                    Log.d(TAG, documentsnapshot.getId() + " => " + documentsnapshot.getData());
                } hallsystem.setRoomList(rooms);
            }
        });
    }


}
