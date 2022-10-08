package com.example.week4.repository;

import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PersonRepository extends CrudRepository<Person, Long> {


    List<Person> getPersonByTeamId(@Param("teamId") Long teamId);


}