package ru.job4j.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.PersonDto;
import ru.job4j.auth.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private final PasswordEncoder passwordEncoder;

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

    @PatchMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable("id") int id, @RequestBody PersonDto personDto) {
        Person person = personService.getById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found. Please, check that such a person exists."
        ));
        var updatedPerson = personService.updatePersonById(id, personDto);
        return ResponseEntity.ok(updatedPerson);
    }

    @PutMapping("/update")
    public ResponseEntity<Person> update(@RequestBody Person person) {
        personService.update(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
