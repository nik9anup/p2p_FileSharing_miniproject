package com.fileshare.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fileshare.util.Config;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class RoomController {
    @FXML private TextField roomNameField;
    @FXML private Button joinRoomButton;
    @FXML private ListView<FileEntry> fileListView;
    @FXML private Button uploadButton;

    private static RoomController instance;
    private Stage stage;

    public RoomController() {
        instance = this;
    }

    public static void createRoomUI() {
    Platform.runLater(() -> {
        try {
            FXMLLoader loader = new FXMLLoader(RoomController.class.getResource("/room.fxml")); // ✅ Corrected path
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Create / Join Room");
            stage.setScene(scene);
            stage.show();

            instance = loader.getController();
            instance.stage = stage;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Failed to load FXML. Make sure room.fxml is in src/main/resources/");
        }
    });
}


    public static void showRoomUI() {
        Platform.runLater(() -> {
            if (instance != null && instance.stage != null) {
                instance.stage.show();
            } else {
                createRoomUI();
            }
        });
    }

        @FXML
private void initialize() {
    joinRoomButton.setOnAction(event -> joinRoom());
    uploadButton.setOnAction(event -> uploadFile());

    
    fileListView.setCellFactory(lv -> new ListCell<FileEntry>() {
        private final Button downloadButton = new Button("Download");

        {
            downloadButton.setOnAction(event -> {
                FileEntry file = getItem();
                if (file != null) {
                    downloadFile(file);
                }
            });
        }

        @Override
        protected void updateItem(FileEntry file, boolean empty) {
            super.updateItem(file, empty);

            if (empty || file == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(file.getName());
                setGraphic(downloadButton);
            }
        }
    });
}

private void joinRoom() {
    String roomName = roomNameField.getText().trim();
    if (roomName.isEmpty()) {
        System.out.println("Enter a valid room name.");
        return;
    }
    System.out.println("Joined Room: " + roomName);
    loadFilesFromRoom(roomName); // ✅ load actual file list from backend
}

    private void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File to Upload");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("Uploading file: " + selectedFile.getAbsolutePath());
            FileTransferHandler.uploadToServer(selectedFile.getAbsolutePath(), roomNameField.getText());



        }
    }

    private void loadFilesFromRoom(String room) {
    String endpoint = Config.getFileListURL(room);
    try {
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        in.close();
        conn.disconnect();
        System.out.println("Full file list JSON: " + response.toString());

        JSONArray jsonArray = new JSONArray(response.toString());

        Platform.runLater(() -> {
            fileListView.getItems().clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject fileObj = jsonArray.getJSONObject(i);
                //String fileId = fileObj.getString("id");
                String fileId = fileObj.optString("id", fileObj.optString("_id", ""));
                String fileName = fileObj.optString("originalFileName", "Unnamed File");
                FileEntry entry = new FileEntry(fileId, fileName);
                fileListView.getItems().add(entry);

                System.out.println("Full file list JSON: " + response.toString());


            }
        });

    } catch (Exception e) {
        System.err.println("Error fetching file list: " + e.getMessage());
    }
}

private void downloadFile(FileEntry file) {
    String endpoint = Config.getDownloadURL(file.getId());
    try (InputStream in = new URL(endpoint).openStream()) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(file.getName());
        File destination = fileChooser.showSaveDialog(stage);
        if (destination == null) return;

        try (OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Downloaded: " + destination.getAbsolutePath(), ButtonType.OK);
        alert.showAndWait();

    } catch (IOException e) {
        System.err.println("Download failed: " + e.getMessage());
    }
}




}
