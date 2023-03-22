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

    public boolean update(Person person) {
        boolean result = false;
        Person findPerson = personRepository.findById(person.getId())
                .orElseThrow(() -> new EntityNotFoundException("Person is not exists"));
                if (!person.equals(findPerson)) {
                    findPerson.setPassword(person.getPassword());
                    personRepository.save(findPerson);
                    return !result;
                }
                return result;
    }

    public boolean delete(int id) {
        Person findPerson = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person is not exists"));
      personRepository.delete(findPerson);
      return true;
    }
}
