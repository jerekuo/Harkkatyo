package com.example.moovi;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    Hall hall;
    Room room;
    String sport;
    String description;
    int resId;
    int participants;
    Date startTime;
    Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public Reservation(Hall hall, Room room, String sport, String description, int resId, int participants, Date startTime, Date endTime) {
        this.hall = hall;
        this.room = room;
        this.sport = sport;
        this.description = description;
        this.resId = resId;
        this.participants = participants;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void makeReservation(Reservation reservation){
        HallSystem hallSystem = HallSystem.getInstance();

        Reservation res = reservation;

        if (checkIfFree(res) == true) {
            hallSystem.addToResList(res);
        } else{
            System.out.println("Varauksen teko ei onnistunut, löytyi päällekkäisyys.");
        }

    }

    public Boolean checkIfFree(Reservation reservation){
        HallSystem hallSystem = HallSystem.getInstance();
        Reservation res = reservation;
        String availability = "True";

        for (Reservation r : hallSystem.getResList()) {
            if (r.room == res.room && r.startTime.compareTo(res.startTime)  < 0 && r.endTime.compareTo(res.endTime) > 0){
                //Listasta löytyy varaus samalle ajalle
                // date vertailu löytyy netistä helposti
                availability = "False";
                break;
            }
        }

        if (availability.equalsIgnoreCase("False")){  // Metodi palauttaa truen jos listavertailun jälkeen ei ole löytynyt päällekkäisyyttä
            return false;                                          // Palauttaa falsen jos lötyy päällekkäisyyttä
        } else{
            return true;
        }

    }

    public void addParticipants(Reservation res){
        res.participants++;

    }




}
