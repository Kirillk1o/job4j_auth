package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.PersonDto;
import ru.job4j.auth.service.PersonService;
import ru.job4j.auth.validation.Operation;

import javax.validation.Valid;
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

    @GetMapping
    public Person findByName(@RequestParam("username") String username) {
        return personService.findByName(username).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found. Please, check username."
        ));
    }

    @PatchMapping("/{id}")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Person> update(@PathVariable("id") int id, @Valid @RequestBody PersonDto personDto) {
        Person person = personService.getById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found. Please, check that such a person exists."
        ));
        var updatedPerson = personService.updatePersonById(id, personDto);
        return ResponseEntity.ok(updatedPerson);
    }

    @PutMapping("/update")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Person> update(@Valid @RequestBody Person person) {
        personService.update(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
