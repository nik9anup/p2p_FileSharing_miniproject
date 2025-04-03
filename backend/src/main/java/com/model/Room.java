package com.fileshare.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rooms")
public class Room {
    @Id
    private String id;
    private String roomName;
    private List<String> users; // Store user IDs

    // Constructor for new room (empty user list)
    public Room(String roomName) {
        this.roomName = roomName;
        this.users = new ArrayList<>();
    }

    // Constructor for room with users
    public Room(String roomName, List<String> users) {
        this.roomName = roomName;
        this.users = users;
    }

    public String getRoomName() { return roomName; }
    public List<String> getUsers() { return users; }

    public void addUser(String userId) {
        if (!users.contains(userId)) {
            users.add(userId);
        }
    }
}
