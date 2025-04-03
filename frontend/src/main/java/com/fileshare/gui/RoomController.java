package com.fileshare.gui;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RoomController {
    @FXML private TextField roomNameField;
    @FXML private Button joinRoomButton;
    @FXML private ListView<String> fileListView;
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
    }

    private void joinRoom() {
        String roomName = roomNameField.getText().trim();
        if (roomName.isEmpty()) {
            System.out.println("Enter a valid room name.");
            return;
        }
        System.out.println("Joined Room: " + roomName);
    }

    private void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File to Upload");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("Uploading file: " + selectedFile.getAbsolutePath());
            FileTransferHandler.sendFile(selectedFile.getAbsolutePath(), "127.0.0.1");
        }
    }
}
