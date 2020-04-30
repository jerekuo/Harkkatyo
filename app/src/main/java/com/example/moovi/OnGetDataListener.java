package com.example.moovi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

public interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(DocumentSnapshot documentSnapshot);
    void onStart();
    void onFailure();
}
