package com.fileshare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fileshare.model.Room;
import com.fileshare.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired private RoomService roomService;

    @PostMapping("/create")
    public Room createRoom(@RequestParam String roomName) {
        return roomService.createRoom(roomName);
    }

    @PostMapping("/join")
    public Room joinRoom(@RequestParam String roomName, @RequestParam String username) {
        return roomService.joinRoom(roomName, username);
    }

    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
}
