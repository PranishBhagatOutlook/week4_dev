package com.example.week4.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Date birthDate;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String university;



    @ManyToOne
    private Team team;

    @ManyToMany
    private Set<Contest> contests = new HashSet();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Contest> getContests() {
        return contests;
    }

    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }
}

