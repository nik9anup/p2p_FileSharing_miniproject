package com.fileshare.controller;

import com.fileshare.model.Room;
import com.fileshare.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/create")
    public Room createRoom(@RequestParam String name, @RequestParam List<String> users) {
        return roomService.createRoom(name, users);
    }

    @GetMapping("/list")
    public List<Room> getRooms() {
        return roomService.getAllRooms();
    }
}
