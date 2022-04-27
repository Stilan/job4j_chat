package ru.job4j.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        var person = this.roomRepository.findById(id);
        return new ResponseEntity<Room>(
                person.orElse(new Room()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
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
