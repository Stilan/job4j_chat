package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.marker.Operation;
import ru.job4j.model.Person;
import ru.job4j.model.Role;
import ru.job4j.model.Room;
import ru.job4j.repository.PersonRepository;
import ru.job4j.repository.RoleRepository;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {


    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder encoder;


    public PersonController(PersonRepository personRepository, BCryptPasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> findAll() {
        List<Person> personList = (List<Person>) this.personRepository.findAll();
        return  ResponseEntity.ok(personList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return ResponseEntity.badRequest().body(this.personRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person is not found. Please, check requisites.")));
    }

    @PostMapping("/sign-up")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        String strName = person.getName();
        if (strName == null) {
            throw new NullPointerException("Person mustn't be empty");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(
                this.personRepository.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        this.personRepository.save(person);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        personRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    
}
