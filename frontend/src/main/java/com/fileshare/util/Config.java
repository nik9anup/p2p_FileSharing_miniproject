package com.fileshare.util;

public class Config {
    public static String SERVER_HOST = "http://localhost:8081"; 

    public static void setHost(String ip) {
        SERVER_HOST = "http://" + ip + ":8081";
    }

    public static String getFileListURL(String room) {
        return SERVER_HOST + "/files/" + room;
    }

    public static String getDownloadURL(String fileId) {
        return SERVER_HOST + "/files/download/" + fileId;
    }

    public static String getUploadURL() {
        return SERVER_HOST + "/files/upload";
    }
}
