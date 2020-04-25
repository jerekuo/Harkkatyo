package com.example.moovi;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HallSystem {
    DatabaseReference reff;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Hall> halls;
    ArrayList<Room> rooms;

    private static HallSystem hallsystem = new HallSystem();

    public ArrayList<Hall> getHallList() {
        return hallList;
    }

    public FirebaseUser user;

    public void setUser (FirebaseUser u) {
        this.user = u;
    }

    public FirebaseUser getUser(){
        return this.user;
    }
    
    private HallSystem(){
        //Creating different halls
        halls = new ArrayList<>();

        /*ArrayList<Room> tennisRoomList = new ArrayList<Room>();
        tennisRoomList.add(new Room("tennisMesta 1", 10, "Kaikki pelaa paitsi Jere", 1));

        ArrayList<Room> fightRoomList = new ArrayList<Room>();
        fightRoomList.add(new Room("TappeluMesta 1", 10, "gegav", 5));

        ArrayList<Room> floorballRoomList = new ArrayList<Room>();
        floorballRoomList.add(new Room("SählyMesta 1", 10, "vasvva", 2));

        ArrayList<Room> basketRoomList = new ArrayList<Room>();
        basketRoomList.add(new Room("KoripalliMesta 1", 10, "vasvvsa", 3));

        ArrayList<Room> padelRoomList = new ArrayList<Room>();
        padelRoomList.add(new Room("WannabetennisMesta 1", 10, "avsvavs", 4));


        Hall tennisHall = new Hall(1, "Tennis Club", "Tennis Avenue 12", tennisRoomList);
        Hall floorBallHall = new Hall(2, "Floorball Club", "Floorball Boulevar 5", floorballRoomList);
        Hall basketBallHall = new Hall(3, "Basketball Club", "Basketball Street 66",basketRoomList);
        Hall padelHall = new Hall(4, "Padel Club", "Padel Road 10",padelRoomList);
        Hall fightHall = new Hall(5, "Fight Club", "Fight Alley 13",fightRoomList);
        halls.add(tennisHall);
        halls.add(floorBallHall);
        halls.add(basketBallHall);
        halls.add(padelHall);
        halls.add(fightHall);*/

        /*reff = FirebaseDatabase.getInstance().getReference().child("halls");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (int i = 1; i<5; i++){
                    String x = (String) Integer.toString(i);
                    String name = dataSnapshot.child(x).child("name").getValue().toString();
                    String address = dataSnapshot.child(x).child("address").getValue().toString();
                    halls.add(new Hall(i,name,address));

                    System.out.println(name);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

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
        setHallList(halls);
    }

    //Arraylist which contains all of the halls
    ArrayList<Hall> hallList = new ArrayList<>();

    public void setHallList(ArrayList<Hall> hallList) {
        this.hallList = hallList;
    }

    public void writeRoomList(String hall){

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
                } setRoomList(rooms);
            }
        });
    }

    public static HallSystem getInstance() {
        return hallsystem;
    }

    public ArrayList<Reservation> resList = new ArrayList<>();

    public ArrayList<Reservation> getResList() {
        return resList;
    }

    public void addToResList(Reservation res){
        ArrayList<Reservation> list = getResList();
        list.add(res);
        setResList(list);
    }

    public void setResList(ArrayList<Reservation> resList) {
        this.resList = resList;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }

    ArrayList<Room> roomList = new ArrayList<>();//Arraylist for all the rooms in the hall




}
