package com.example.moovi;

import java.util.ArrayList;

public class HallSystem {

    private static HallSystem hallsystem = new HallSystem();

    public static HallSystem getInstance() { return hallsystem; }

    //Arraylist which contains all of the halls
    ArrayList<Hall> hallList = new ArrayList<>();


    private HallSystem(){
        //Creating different halls
        ArrayList<Hall> halls = new ArrayList<>();

        Hall tennisHall = new Hall(1, "Tennis Club", "Tennis Avenue 12");
        Hall floorBallHall = new Hall(2, "Floorball Club", "Floorball Boulevard 5");
        Hall basketBallHall = new Hall(3, "Basketball Club", "Basketball Street 66");
        Hall padelHall = new Hall(4, "Padel Club", "Padel Road 10");
        Hall fightHall = new Hall(5, "Fight Club", "Fight Alley 13");
        halls.add(tennisHall);
        halls.add(floorBallHall);
        halls.add(basketBallHall);
        halls.add(padelHall);
        halls.add(fightHall);

        //Setting halls to hallList
        setHallList(halls);
    }



    public void setHallList(ArrayList<Hall> hallList) {
        this.hallList = hallList;
    }

    public ArrayList<Hall> getHallList() {
        return hallList;
    }


    public ArrayList<Reservation> resList = new ArrayList<>();

    public ArrayList<Reservation> getResList() {
        return resList;
    }

    public void addToResList(Reservation res){
        ArrayList<Reservation> list = getResList();
        list.add(res);
        setResList(list);
    }

    public void setResList(ArrayList<Reservation> resList) {
        this.resList = resList;
    }




}
