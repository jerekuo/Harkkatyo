package com.example.moovi;

import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;

public class HallSystem {

    //Class variables

    private static HallSystem hallsystem = new HallSystem();
    public FirebaseUser user;
    public User useri;
    public ArrayList<Reservation> resList = new ArrayList<>();
    //Arraylist which contains all of the halls
    ArrayList<Hall> hallList = new ArrayList<>();


    //GETTERS AND SETTER
    public ArrayList<Hall> getHallList() {
        return hallList;
    }

    public User getUseri() {
        return useri;
    }

    public void setUseri(User useri) {
        this.useri = useri;
    }

    public void setUser (FirebaseUser u) {
        this.user = u;
    }

    public FirebaseUser getUser(){
        return this.user;
    }

    public void setHallList(ArrayList<Hall> hallList) {
        this.hallList = hallList;
    }

    public static HallSystem getInstance() {
        return hallsystem;
    }

    public ArrayList<Reservation> getResList() {
        return resList;
    }

    public void setResList(ArrayList<Reservation> resList) {
        this.resList = resList;
    }


    private HallSystem(){
        //null constructor
    }


    //CLASS METHODS

    public void addToResList(Reservation res){
        ArrayList<Reservation> list = getResList();
        list.add(res);
        setResList(list);
    }








}
