package com.example.moovi;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//Interface is used to make application wait for database to complete queries.
public interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(DocumentSnapshot documentSnapshot);
    void onSuccess(@NonNull Task<QuerySnapshot> task);
    void onStart();
    void onFailure();
}
