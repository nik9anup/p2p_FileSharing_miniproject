package com.fileshare.service;

import com.fileshare.model.Room;
import com.fileshare.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(String name, List<String> users) {
        return roomRepository.save(new Room(name, users));
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
