package com.example.week4.services;


import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.Team;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
@Transactional
public class SuperRepository {
    @PersistenceContext
    private EntityManager em;

    public void populate() {
        Contest contest = createContest(50, new Date("01/01/2022"), "45th World Finals",
                true, new Date("01/01/2021"), new Date("12/31/2021"), null, true);
        Contest contest1 = createContest(50, new Date("01/01/2022"), "2022 NAC Regionals",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);

        Person person1 = createPerson(new Date("1/1/1993"), "1bhagatpranish@gmail.com", "Pranish Bhagat", "Baylor University");
        Person person2 = createPerson(new Date("2/2/1994"), "2bhagatpranish@gmail.com", "2Pranish Bhagat", "2Baylor University");
        Person person3 = createPerson(new Date("3/3/1995"), "3bhagatpranish@gmail.com", "3Pranish Bhagat", "3Baylor University");
        Person person4 = createPerson(new Date("4/4/1996"), "4bhagatpranish@gmail.com", "4Pranish Bhagat", "4Baylor University");
        Person person5 = createPerson(new Date("5/5/1997"), "5bhagatpranish@gmail.com", "5Pranish Bhagat", "5Baylor University");
        Person person6 = createPerson(new Date("6/6/1998"), "6bhagatpranish@gmail.com", "6Pranish Bhagat", "6Baylor University");
        Person person7 = createPerson(new Date("7/7/1999"), "7bhagatpranish@gmail.com", "7Pranish Bhagat", "7Baylor University");
        Person person8 = createPerson(new Date("8/8/2000"), "8bhagatpranish@gmail.com", "8Pranish Bhagat", "8Baylor University");
        Person person9 = createPerson(new Date("9/9/2001"), "9bhagatpranish@gmail.com", "9Pranish Bhagat", "9Baylor University");
        Person person10 = createPerson(new Date("10/10/2002"), "10bhagatpranish@gmail.com", "10Pranish Bhagat", "10Baylor University");
        Person person11 = createPerson(new Date("11/11/2003"), "11bhagatpranish@gmail.com", "11Pranish Bhagat", "11Baylor University");
        Person person12 = createPerson(new Date("12/12/2004"), "12bhagatpranish@gmail.com", "12Pranish Bhagat", "12Baylor University");


        Person contestManager = createPerson(new Date("10/12/1993"), "contestmanager@gmail.com", "Contest Manager", "Contest Manager University");
        contest1.getContestManager().add(contestManager);
        contest.getContestManager().add(contestManager);
        Team team1 = createTeam("TeamA", null, Team.State.PENDING, true);
        team1.getContestant().add(person1);
        team1.getContestant().add(person2);
        team1.getContestant().add(person3);
        team1.setCoach(person4);
        team1.setContest(contest1);
        Team team2 = createTeam("TeamB", null, Team.State.PENDING, true);
        team2.getContestant().add(person5);
        team2.getContestant().add(person6);
        team2.getContestant().add(person7);
        team2.setCoach(person8);
        team2.setContest(contest1);
        Team team3 = createTeam("TeamC", null, Team.State.PENDING, true);
        team3.getContestant().add(person9);
        team3.getContestant().add(person10);
        team3.getContestant().add(person11);
        team3.setCoach(person12);
        team3.setContest(contest1);
//        Team team4 = createTeam("TeamD",null, Team.State.PENDING);
    }

    private Contest createContest(Integer capacity, Date date,
                                  String name, Boolean registration_allowed,
                                  Date registration_from, Date registration_to, Long superContestId, Boolean writable) {
        Contest contest = new Contest();
        contest.setCapacity(capacity);
        contest.setDate(date);
        contest.setName(name);
        contest.setRegistration_allowed(registration_allowed);
        contest.setRegistration_from(registration_from);
        contest.setRegistration_to(registration_to);
        contest.setSuperContestId(superContestId);
        contest.setWritable(writable);
        em.persist(contest);
        return contest;
    }

//    private Team createTeam(String name, Integer rank, Team.State state){}

    private Person createPerson(Date birthDate, String email, String name, String university) {
        Person person = new Person();
        person.setBirthDate(birthDate);
        person.setEmail(email);
        person.setName(name);
        person.setUniversity(university);
        em.persist(person);
        return person;
    }

    private Team createTeam(String name, Integer rank, Team.State state, Boolean writable) {
        Team team = new Team();
        team.setName(name);
        team.setRank(rank);
        team.setState(state);
        team.setWritable(writable);
        em.persist(team);
        return team;
    }



    public List<Team> getTeams() {
        return em.createQuery("SELECT c FROM Team c").getResultList();
    }

    public List<Person> getPersons() {
        return em.createQuery("SELECT c FROM Person c").getResultList();
    }

    public List<Contest> getContests() {
        return em.createQuery("SELECT c FROM Contest c").getResultList();
    }



}
