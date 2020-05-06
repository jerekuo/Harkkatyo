package com.example.moovi;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    Hall hall;
    Room room;
    String description;
    int resId;
    int participants;
    String startTime;
    String endTime;
    String resDate;

    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
    }



    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }




    public ArrayList<User> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(ArrayList<User> participantList) {
        this.participantList = participantList;
    }

    ArrayList<User> participantList = new ArrayList<>();

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public Reservation(Hall hall, Room room,  String description, int resId, int participants, String startTime, String endTime, String resDate) {
        this.hall = hall;
        this.room = room;
        this.description = description;
        this.resId = resId;
        this.participants = participants;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resDate = resDate;
    }

    public Reservation(){
        //Null constructor
    }



    public void addParticipants(Reservation res){
        res.participants++;

    }




}
