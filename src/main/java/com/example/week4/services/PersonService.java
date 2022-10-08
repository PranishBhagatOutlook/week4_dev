package com.example.week4.services;

import com.example.week4.model.Person;
import com.example.week4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findPersonById(@PathVariable("id") Long id) {
        Person person = personRepository.findById(id).get();
        if (person.getName() != null) {
            return person;
        }
        return null;
    }


    public List<Person> getPersonByTeamId(Long teamId){
        return personRepository.getPersonByTeamId(teamId);
    }

    public Person createPerson(Person person){
        return personRepository.save(person);
    }
}
