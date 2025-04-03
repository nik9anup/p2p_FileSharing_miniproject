package com.fileshare.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    private String username;
    private String password;

    public User(String username, String password) {  // Fix: Constructor with two arguments
        this.username = username;
        this.password = password;
    }

    public String getPassword() { return password; }
}
