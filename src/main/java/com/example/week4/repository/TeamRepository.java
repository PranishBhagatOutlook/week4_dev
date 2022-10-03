package com.example.week4.repository;

import com.example.week4.model.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public interface TeamRepository extends CrudRepository<Team, Long> {
//    public Team findTeamById(Long id);


    List<Team> findByContestId(@Param("contestId") Long contestId);

}
