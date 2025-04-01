package com.fileshare.model;

import java.util.List;

public class Room {
    private String roomName;
    private List<String> users;

    // Constructor
    public Room(String roomName, List<String> users) {
        this.roomName = roomName;
        this.users = users;
    }

    // Getters and Setters
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
