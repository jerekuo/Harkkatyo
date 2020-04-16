package com.example.moovi;

import java.util.ArrayList;

public class Hall {
    int hallId;
    String hallName;

    public Hall(int hallId, String hallName, String hallLocation) {
        this.hallId = hallId;
        this.hallName = hallName;
        this.hallLocation = hallLocation;
    }

    String hallLocation;
    ArrayList<Room> roomList = new ArrayList<>(); //Arraylist for all the rooms in the hall
}
