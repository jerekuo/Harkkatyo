package com.example.moovi;

import java.util.ArrayList;

public class HallSystem {


    private HallSystem hallsystem = new HallSystem();

    public ArrayList<Hall> getHallList() {
        return hallList;
    }

    public void setHallList(ArrayList<Hall> hallList) {
        this.hallList = hallList;
    }

    ArrayList<Hall> hallList = new ArrayList<>(); //Arraylist which contains all of the halls

    public HallSystem getInstance() {
        return this.hallsystem;
    }
}
