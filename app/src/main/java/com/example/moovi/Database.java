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
}
