package com.fileshare.gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileController {

    public static List<String> getFileList(String roomName) {
        List<String> files = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:8081/files/upload" + roomName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            String json = jsonBuilder.toString();
            // Very simple parsing: assume filenames are in `"filename":"<name>"` format
            String[] parts = json.split("\"filename\":\"");
            for (int i = 1; i < parts.length; i++) {
                String filename = parts[i].split("\"")[0];
                files.add(filename);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }
}
