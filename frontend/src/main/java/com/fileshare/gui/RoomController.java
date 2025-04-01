package com.fileshare.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomController {

    public static void showRoomUI() {
        Stage roomStage = new Stage();
        roomStage.setTitle("Join a Room");

        TextField roomField = new TextField();
        roomField.setPromptText("Enter Room Name");

        Button joinBtn = new Button("Join");
        joinBtn.setOnAction(e -> {
            String roomName = roomField.getText();
            System.out.println("Joining room: " + roomName);
            FileTransferHandler.startFileReceiver("downloads/");
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(roomField, joinBtn);

        Scene scene = new Scene(layout, 300, 200);
        roomStage.setScene(scene);
        roomStage.show();
    }
}
