package com.example.moovi;

import java.util.ArrayList;

public class HallSystem {

    private static HallSystem hallsystem = new HallSystem();

    public ArrayList<Hall> getHallList() {
        return hallList;
    }

    private HallSystem(){
        //Creating different halls
        ArrayList<Hall> halls = new ArrayList<>();

        ArrayList<Room> tennisRoomList = new ArrayList<Room>();
        tennisRoomList.add(new Room("tennisMesta 1", 10, "Kaikki pelaa paitsi Jere", 1));

        ArrayList<Room> fightRoomList = new ArrayList<Room>();
        fightRoomList.add(new Room("TappeluMesta 1", 10, "gegav", 5));

        ArrayList<Room> floorballRoomList = new ArrayList<Room>();
        floorballRoomList.add(new Room("SählyMesta 1", 10, "vasvva", 2));

        ArrayList<Room> basketRoomList = new ArrayList<Room>();
        basketRoomList.add(new Room("KoripalliMesta 1", 10, "vasvvsa", 3));

        ArrayList<Room> padelRoomList = new ArrayList<Room>();
        padelRoomList.add(new Room("WannabetennisMesta 1", 10, "avsvavs", 4));


        Hall tennisHall = new Hall(1, "Tennis Club", "Tennis Avenue 12", tennisRoomList);
        Hall floorBallHall = new Hall(2, "Floorball Club", "Floorball Boulevar 5", floorballRoomList);
        Hall basketBallHall = new Hall(3, "Basketball Club", "Basketball Street 66",basketRoomList);
        Hall padelHall = new Hall(4, "Padel Club", "Padel Road 10",padelRoomList);
        Hall fightHall = new Hall(5, "Fight Club", "Fight Alley 13",fightRoomList);
        halls.add(tennisHall);
        halls.add(floorBallHall);
        halls.add(basketBallHall);
        halls.add(padelHall);
        halls.add(fightHall);

        //Setting halls to hallList
        setHallList(halls);
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

    public void addToResList(Reservation res){
        ArrayList<Reservation> list = getResList();
        list.add(res);
        setResList(list);
    }

    public void setResList(ArrayList<Reservation> resList) {
        this.resList = resList;
    }




}
