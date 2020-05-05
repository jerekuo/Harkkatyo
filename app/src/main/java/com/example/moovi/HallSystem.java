package com.example.moovi;



import com.google.firebase.auth.FirebaseUser;



import java.util.ArrayList;

//Singleton which is used to store all the objects we get from database.

public class HallSystem {

    public Reservation getChosenRes() {
        return chosenRes;
    }

    public void setChosenRes(Reservation chosenRes) {
        this.chosenRes = chosenRes;
    }

    public Reservation chosenRes;


    private static HallSystem hallsystem = new HallSystem();

    public ArrayList<Hall> getHallList() {
        return hallList;
    }

    public FirebaseUser user;

    public User getUseri() {
        return useri;
    }

    public void setUseri(User useri) {
        this.useri = useri;
    }

    public User useri;

    public User getEditUser() {
        return editUser;
    }

    public void setEditUser(User editUser) {
        this.editUser = editUser;
    }

    public User editUser;

    public void setUser (FirebaseUser u) {
        this.user = u;
    }

    public FirebaseUser getUser(){
        return this.user;
    }

    private HallSystem(){

    }

    //Arraylist which contains all of the halls
    ArrayList<Hall> hallList = new ArrayList<>();

    public void setHallList(ArrayList<Hall> hallList) {
        this.hallList = hallList;
    }


    public static HallSystem getInstance() {
        return hallsystem;
    }

    public ArrayList<Reservation> resList = new ArrayList<>();

    public ArrayList<Reservation> getResList() {
        return resList;
    }

    public ArrayList<Reservation> getCurUserResList() {
        return curUserResList;
    }

    public void setCurUserResList(ArrayList<Reservation> curUserResList) {
        this.curUserResList = curUserResList;
    }

    public ArrayList<Reservation> curUserResList = new ArrayList<>();

    public void addToResList(Reservation res){
        Database db = Database.getInstance();
        db.addReservation(res);
        //ArrayList<Reservation> list = getResList();
        //list.add(res);
        //setResList(list);
    }

    public void setResList(ArrayList<Reservation> resList) {
        this.resList = resList;
    }








}
