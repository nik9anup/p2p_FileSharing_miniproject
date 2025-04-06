package com.fileshare.service;

import com.fileshare.model.Room;
import com.fileshare.model.User;
import com.fileshare.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class RoomService {
    @Autowired private RoomRepository roomRepository;

    public Room joinRoom(String roomName, String username) {
        Optional<Room> existingRoom = roomRepository.findByRoomName(roomName);

        Room room;
        if (existingRoom.isPresent()) {
            room = existingRoom.get();
        } else {
            room = new Room(roomName);
        }

        // Add user to room
        room.addUser(username);
        roomRepository.save(room);
        return room;
    }

    public Room createRoom(String roomName) {
        Room room = new Room(roomName);
        return roomRepository.save(room);
    }

    // ✅ Fix: Implement getAllRooms method
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
