package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.handler.GlobalExceptionHandler;
import ru.job4j.auth.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        if (person.getUsername() == null || person.getPassword() == null) {
            throw new IllegalArgumentException("Username and password are required.");
        }
        if (person.getPassword().length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        if (!isValidUsername(person.getUsername())) {
            throw new IllegalArgumentException("Invalid username. Username must contain only letters, numbers, and underscores.");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        var entity = ResponseEntity.status(HttpStatus.CREATED)
                .header("CustomHeader")
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.save(person));
        return entity;
    }

    private boolean isValidUsername(String username) {
        String pattern = "^[a-zA-Z0-9_]*$";
        return username.matches(pattern);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping
    public Person findByName(@RequestParam String username) {
        return personService.findByName(username).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found. Please, check username."
        ));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getMessage());
    }
}
