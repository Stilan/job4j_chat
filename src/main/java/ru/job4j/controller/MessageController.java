package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Message;
import ru.job4j.model.Person;
import ru.job4j.model.Room;
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
    public ResponseEntity<List<Message>> findAll() {
        List<Message> messageList = (List<Message>) this.messageRepository.findAll();
        return  ResponseEntity.ok(messageList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        return ResponseEntity.badRequest().body(this.messageRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Message is not found. Please, check requisites.")));
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        String strName = message.getName();
        if (strName == null) {
            throw new NullPointerException("Message mustn't be empty");
        }
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
