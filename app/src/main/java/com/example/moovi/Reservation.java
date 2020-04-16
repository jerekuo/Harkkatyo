package com.example.moovi;

import java.util.ArrayList;

public class Reservation {
    Hall hall;
    Room room;
    String sport;
    String description;
    int resId;
    int participants;

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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public Reservation(Hall hall, Room room, String sport, String description, int resId, int participants) {
        this.hall = hall;
        this.room = room;
        this.sport = sport;
        this.description = description;
        this.resId = resId;
        this.participants = participants;
    }
}
