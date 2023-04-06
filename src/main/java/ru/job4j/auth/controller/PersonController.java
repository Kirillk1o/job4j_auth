package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/")
    public List<Person> findAll() {
        return personService.getAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable int id) {
        return personService.getById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found. Please, check that such a person exists."
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id, @RequestBody Person person) {
        personService.update(id, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
