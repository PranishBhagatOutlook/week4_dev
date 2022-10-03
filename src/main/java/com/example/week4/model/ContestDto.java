package com.example.week4.model;


import java.util.Date;

public class ContestDto {
    private String name;
    private Integer capacity ;

    private Date date;
    private Boolean registration_allowed;
    private Date registration_from;
    private Date registration_to;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getRegistration_allowed() {
        return registration_allowed;
    }

    public void setRegistration_allowed(Boolean registration_allowed) {
        this.registration_allowed = registration_allowed;
    }

    public Date getRegistration_from() {
        return registration_from;
    }

    public void setRegistration_from(Date registration_from) {
        this.registration_from = registration_from;
    }

    public Date getRegistration_to() {
        return registration_to;
    }

    public void setRegistration_to(Date registration_to) {
        this.registration_to = registration_to;
    }

    public ContestDto(String name, Integer capacity, Date date, Boolean registration_allowed,
                      Date registration_from, Date registration_to) {
        this.name = name;
        this.capacity = capacity;
        this.date = date;
        this.registration_allowed = registration_allowed;
        this.registration_from = registration_from;
        this.registration_to = registration_to;

    }



}