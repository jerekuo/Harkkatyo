package com.example.moovi;

public class Room {
    String name;
    int capacity;

    public Room(String name, int capacity, String description, int roomId) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.roomId = roomId;
    }

    String description;
    int roomId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    @Override
    public String toString() {
        return this.getName();
    }


}
