package com.fileshare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
public class FileMetadata {

    @Id
    private String id; // This should match the GridFS file ID

    private String originalFileName;
    private String room;

    // No-arg constructor for Spring Data
    public FileMetadata() {
    }

    // All-args constructor
    public FileMetadata(String id, String room, String originalFileName) {
        this.id = id;
        this.room = room;
        this.originalFileName = originalFileName;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getRoom() {
        return room;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
