package ru.job4j.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Role;
import ru.job4j.repository.RoleRepository;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return (List<Role>) this.roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        var person = this.roleRepository.findById(id);
        return new ResponseEntity<Role>(
                person.orElse(new Role()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(
                this.roleRepository.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Role role) {
        this.roleRepository.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        roleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
