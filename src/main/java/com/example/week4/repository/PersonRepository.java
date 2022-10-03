package com.example.week4.repository;

import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends CrudRepository<Person, Long> {
//    public Team findTeamById(Long id);

}