package com.fileshare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
public class FileMetadata {
    @Id
    private String id;
    private String room;
    private String originalFileName;

    // Constructor with correct number of arguments
    public FileMetadata(String room, String originalFileName) {
        this.room = room;
        this.originalFileName = originalFileName;
    }

    public String getRoom() { return room; }
    public String getOriginalFileName() { return originalFileName; }
}
