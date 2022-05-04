package ru.job4j.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Room;
import ru.job4j.repository.RoomRepository;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return (List<Room>) this.roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        return ResponseEntity.badRequest().body(this.roomRepository.findById(id).
              orElseThrow(() -> new ResponseStatusException(
                      HttpStatus.NOT_FOUND, "Room is not found. Please, check requisites.")));

    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        String strName = room.getName();
        if (strName == null) {
            throw new NullPointerException("Room mustn't be empty");
        }
        return new ResponseEntity<>(
                this.roomRepository.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        this.roomRepository.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        roomRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
