package com.example.moovi;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Database {
    HallSystem hallsystem = HallSystem.getInstance();
    private static Database instance = new Database();
    FirebaseUser user = HallSystem.getInstance().getUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();



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
}
