package com.fileshare.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("P2P File Sharing");

        Label title = new Label("P2P File Sharing");
        title.setFont(new Font(20));

        Button createRoomBtn = new Button("Create Room");
        createRoomBtn.setOnAction(e -> RoomController.createRoomUI());

        Button joinRoomBtn = new Button("Join Room");
        joinRoomBtn.setOnAction(e -> RoomController.showRoomUI());

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(createRoomBtn, joinRoomBtn);

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, buttons);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
