package com.fileshare.controller;

import com.fileshare.model.Room;
import com.fileshare.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired private RoomService roomService;

    @PostMapping("/create")
    public Room createRoom(@RequestParam String roomName) {
        return roomService.createRoom(roomName);
    }

    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
}
