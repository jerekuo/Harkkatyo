package com.example.moovi;

import java.util.ArrayList;

public class Hall {
    //CLASS VARIABLES
    int hallId;
    String hallName;
    ArrayList<Room> roomList;
    String hallLocation;

    //GETTERS AND SETTERS
    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getHallLocation() {
        return hallLocation;
    }

    public void setHallLocation(String hallLocation) {
        this.hallLocation = hallLocation;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }



    //CONSTRUCTOR
    public Hall(int hallId, String hallName, String hallLocation, ArrayList<Room> roomList) {
        this.hallId = hallId;
        this.hallName = hallName;
        this.hallLocation = hallLocation;
        this.roomList = roomList;
    }





    @Override
    public String toString() {
        return this.hallName;
    }


}
