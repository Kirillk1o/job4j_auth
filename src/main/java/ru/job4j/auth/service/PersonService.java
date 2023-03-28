package ru.job4j.auth.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import javax.persistence.EntityNotFoundException;

import java.util.List;
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

    public Person update(int id, Person person) {
        Person findPerson = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person with id: " + id + " is not exists"));
            findPerson.setPassword(person.getPassword());
            personRepository.save(findPerson);
        return findPerson;
    }


    public void delete(int id) {
        if (!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person with id: " + id + " does not exist");
        }
        personRepository.deleteById(id);
    }
}
