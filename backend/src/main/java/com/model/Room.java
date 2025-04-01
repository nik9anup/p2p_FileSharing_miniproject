package com.fileshare.controller;

import com.fileshare.model.Room;
import com.fileshare.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping("/create")
    public Room createRoom(@RequestParam String roomName) {
        Room room = new Room(roomName);
        return roomRepository.save(room);
    }

    @GetMapping("/list")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
