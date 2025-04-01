package com.fileshare.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("P2P File Sharing");

        Button joinRoomBtn = new Button("Join Room");
        joinRoomBtn.setOnAction(e -> RoomController.showRoomUI());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(joinRoomBtn);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
