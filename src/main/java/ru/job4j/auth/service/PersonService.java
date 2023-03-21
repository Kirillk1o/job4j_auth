package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Optional<Person> getById(int personId) {
    return personRepository.findById(personId);
    }

    public boolean delete(Person person) {
        boolean rsl = false;
        if (person == null) {
            return rsl;
        }
      personRepository.delete(person);
        return !rsl;
    }
}
