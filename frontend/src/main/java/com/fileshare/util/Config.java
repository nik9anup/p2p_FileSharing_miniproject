package com.fileshare.util;

public class Config {
    private static String SERVER_HOST = "http://localhost:8081";

    public static void setHost(String ip) {
        if (ip != null && !ip.isEmpty()) {
            SERVER_HOST = "http://" + ip + ":8081";
        }
    }

    public static String getFileListURL(String room) {
        if (room == null || room.isEmpty()) {
            System.err.println("Error: Room name is empty in getFileListURL()");
            return "";
        }
        String url = SERVER_HOST + "/files/" + room;
        System.out.println("File List URL: " + url);
        return url;
    }

    public static String getDownloadURL(String fileId) {
        if (fileId == null || fileId.isEmpty()) {
            System.err.println("Error: File ID is empty in getDownloadURL()");
            return "";
        }
        String url = SERVER_HOST + "/files/download/" + fileId;
        System.out.println("Download URL: " + url);
        return url;
    }

    public static String getUploadURL() {
        String url = SERVER_HOST + "/files/upload";
        System.out.println("Upload URL: " + url);
        return url;
    }

    public static String getServerHost() {
        return SERVER_HOST;
    }
}
