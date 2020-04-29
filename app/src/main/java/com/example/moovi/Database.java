package com.example.moovi;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import static androidx.constraintlayout.widget.Constraints.TAG;



public class Database {
    //CLASS VARIABLES

    HallSystem hallsystem = HallSystem.getInstance();
    private static Database instance = new Database();
    FirebaseUser user = HallSystem.getInstance().getUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Hall> halls;


    //GETTERS AND SETTERS
    public static Database getInstance() {
        return instance;
    }



    private Database() {
        //null constructor
    }

    //CLASS METHODS

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
    public void getUserFromDB() {
        final User[] useri = new User[1];
        DocumentReference docRef = db.collection("Users").document(user.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                useri[0] = documentSnapshot.toObject(User.class);
                hallsystem.setUseri(useri[0]);
            }
        });

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

    public ArrayList<Room> writeRoomList(final String hall){
        final ArrayList<Room> rooms = new ArrayList<>();
        //final String h = hall;
        db.collection("AllHalls").document(hall).collection("Rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int i = 0;

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()){
                    i++;
                    String name = documentsnapshot.getData().get("name").toString();
                    int cap = Integer.parseInt(documentsnapshot.getData().get("capacity").toString());

                    rooms.add(new Room(name,cap,"Kaikki pelaa",i));


                    Log.d(TAG, documentsnapshot.getId() + " => " + documentsnapshot.getData());
                }
            }
        }); return rooms;

    }

    public void addReservation(Reservation r) {

        db.collection("Reservations").document(user.getEmail()+","+r.resDate+","+r.startTime)
                .set(r)
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
    //U

    public void writeReservationList(){
        final ArrayList<Reservation> reservations = new ArrayList<>();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()){

                    reservations.add((Reservation) documentsnapshot.toObject(Reservation.class));
                    System.out.println("############ Reservation listaan lisätään:  "+ documentsnapshot.toObject(Reservation.class).startTime);


                    Log.d(TAG, documentsnapshot.getId() + " => " + documentsnapshot.getData());
                }
            }
        }); hallsystem.setResList(reservations);

    }

    public void getUserFromDBemail(String email) {
        System.out.println("GETUSERFROMDBEMAIL KAYNNISTYY JA SAI EMAILIN:  " + email);
        final User[] useri = new User[1];
        DocumentReference docRef = db.collection("Users").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                useri[0] = documentSnapshot.toObject(User.class);
                hallsystem.setEditUser(useri[0]);
                System.out.println(useri[0].getFirstName() + "   SAATU USERI");
                System.out.println("KAYTTAJAN HAKU ON VALMIS");
            }
        });


    }

    public ArrayList getReservationsByDate(int day, int month, int year) {
        String sMonth;
        String sDay;

        if(month < 10) {
            sMonth = "0" + month;
        } else {
            sMonth = Integer.toString(month);
        }

        if(day < 10) {
            sDay = "0" + day;
        } else {
            sDay = Integer.toString(day);
        }



        final String date = year +"-" + sMonth +"-"+ sDay;
        final ArrayList<Reservation> reservations = new ArrayList<>();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()){

                    if (documentsnapshot.toObject(Reservation.class).getResDate().equalsIgnoreCase(date)) {
                        System.out.println("LISATAAN LISTAAN KOSKA: "+date + " == " + documentsnapshot.toObject(Reservation.class).getResDate());
                        reservations.add((Reservation) documentsnapshot.toObject(Reservation.class));
                        System.out.println("############ Reservation listaan lisätään:  "+ documentsnapshot.toObject(Reservation.class).startTime);
                    } else {
                        System.out.println("EI LISATA LISTAAN KOSKA: "+date + " != " + documentsnapshot.toObject(Reservation.class).getResDate());
                    }

                }
            }
        });

        return reservations;

    }




}
