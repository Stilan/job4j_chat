package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Message;
import ru.job4j.repository.MessageRepository;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return (List<Message>) this.messageRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return new ResponseEntity<>(
                this.messageRepository.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        this.messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = messageRepository.findById(id).get();
        this.messageRepository.delete(message);
        return ResponseEntity.ok().build();
    }
}
