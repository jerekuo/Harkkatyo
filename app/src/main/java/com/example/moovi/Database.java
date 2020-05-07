package com.example.moovi;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    //Method overwrites the user in database with new details. Used when updating existing user information.
    public void editUser(User u) {

        db.collection("Users").document(u.getEmail())
                .set(u)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    //Method for getting users details from db for currently logged in user
    public void getUserFromDB(final OnGetDataListener listener) {
        listener.onStart();
        DocumentReference docRef = db.collection("Users").document(user.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                listener.onSuccess(documentSnapshot);

            }
        });

    }


    //Method sets arraylist containing all the halls to hallsystem singleton for easy access.
    public void writeHallList() {
        halls = new ArrayList<>();
        db.collection("AllHalls")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int i = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getData().get("name").toString();
                                String address = document.getData().get("address").toString();
                                ArrayList<Room> list = writeRoomList(document.getId());


                                halls.add(new Hall(i, name, address, list));

                                i++;
                            }
                        } else {

                        }
                    }
                });

        //Setting halls to hallList
        hallsystem.setHallList(halls);
    }

    //Method returns arraylist which contains all the rooms in the database.
    public ArrayList<Room> writeRoomList(final String hall) {
        final ArrayList<Room> rooms = new ArrayList<>();
        //final String h = hall;
        db.collection("AllHalls").document(hall).collection("Rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int i = 0;

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()) {
                    i++;
                    String name = documentsnapshot.getData().get("name").toString();
                    int cap = Integer.parseInt(documentsnapshot.getData().get("capacity").toString());

                    rooms.add(new Room(name, cap, "Kaikki pelaa", i));


                }
            }
        });
        return rooms;

    }

    //adds reservation to the database.
    public void addReservation(Reservation r) {

        db.collection("Reservations").document(user.getEmail() + "," + r.resDate + "," + r.startTime)
                .set(r)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    //U
    public void adminAddReservation(Reservation r) {
        db.collection("Reservations").document(HallSystem.getInstance().getEditEmail() + "," + r.resDate + "," + r.startTime)
                .set(r)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    //writes all reservations in the database to a list and sets it to hallsystem for easy access.
    public void writeReservationList() {
        final ArrayList<Reservation> reservations = new ArrayList<>();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()) {

                    reservations.add(documentsnapshot.toObject(Reservation.class));
                }

            }
        });

        hallsystem.setResList(reservations);
    }

    //Method for deleting reservations from DB
    public void deleteReservation() {
        final Reservation res = HallSystem.getInstance().getChosenRes();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()) {
                    if (documentsnapshot.getId().equalsIgnoreCase(user.getEmail() + "," + res.getResDate() + "," + res.getStartTime())) { //Finds the correct reservation.
                        deleteRes(user.getEmail() + "," + res.getResDate() + "," + res.getStartTime());
                    }
                }

            }
        });
    }

    //Method for admin editing and deleting reservation.
    public void adminDeleteReservation() {
        final String email = HallSystem.getInstance().getEditEmail();
        final Reservation res = HallSystem.getInstance().getChosenRes();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()) {
                    if (documentsnapshot.getId().equalsIgnoreCase(email + "," + res.getResDate() + "," + res.getStartTime())) { //Finds the correct reservation.
                        deleteRes(email + "," + res.getResDate() + "," + res.getStartTime());
                    }
                }

            }
        });
    }

    //Adds new reservation with edited information and deletes the old one from db.
    public void editReservation(final Reservation r) {
        final Reservation res = HallSystem.getInstance().getChosenRes();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()) {
                    if (documentsnapshot.getId().equalsIgnoreCase(user.getEmail() + "," + res.getResDate() + "," + res.getStartTime())) {
                        deleteRes(user.getEmail() + "," + res.getResDate() + "," + res.getStartTime());
                        addReservation(r);
                    }
                }

            }
        });
    }

    public void adminEditReservation(final Reservation r) {
        final String email = hallsystem.getEditEmail();
        final Reservation res = HallSystem.getInstance().getChosenRes();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()) {
                    if (documentsnapshot.getId().equalsIgnoreCase(email + "," + res.getResDate() + "," + res.getStartTime())) {
                        deleteRes(email + "," + res.getResDate() + "," + res.getStartTime());
                        adminAddReservation(r);
                    }
                }

            }
        });
    }

    //writes reservationlist for the given user. Uses Interface to make app to wait until DB completes query.
    public void writeCurrentUserReservationList(final String email, final OnGetDataListener listener) {
        listener.onStart();
        final ArrayList<Reservation> reservations = new ArrayList<>();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                listener.onSuccess(task);

            }
        });
    }

    //Sets given user to hallsystem for easy access.
    public void getUserFromDBemail(String email) {
        final User[] useri = new User[1];
        DocumentReference docRef = db.collection("Users").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                useri[0] = documentSnapshot.toObject(User.class);
                hallsystem.setEditUser(useri[0]);
            }
        });


    }


    //Method returns arrayList<Reservation> which contains all the reservations in the given date.
    public ArrayList getReservationsByDate(int day, int month, int year) {
        String sMonth;
        String sDay;

        //Checks if month or day is only one digit and converts it to 2 digits.
        if (month < 10) {
            sMonth = "0" + month;
        } else {
            sMonth = Integer.toString(month);
        }

        if (day < 10) {
            sDay = "0" + day;
        } else {
            sDay = Integer.toString(day);
        }


        final String date = year + "-" + sMonth + "-" + sDay;
        final ArrayList<Reservation> reservations = new ArrayList<>();
        db.collection("Reservations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentsnapshot : task.getResult()) {

                    if (documentsnapshot.toObject(Reservation.class).getResDate().equalsIgnoreCase(date)) {
                        reservations.add(documentsnapshot.toObject(Reservation.class));

                    } else {

                    }

                }
            }
        });

        return reservations;

    }

    //Deletes reservation from db, Takes the documents name as parameter.
    public void deleteRes(final String s) {
        db.collection("Reservations").document(s).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("Reservation " + s + "Removed");
            }
        });
    }


}
