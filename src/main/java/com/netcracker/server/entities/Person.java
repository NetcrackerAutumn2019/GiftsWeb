package com.netcracker.server.entities;


import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;

@Entity
@Table
public class Person {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Person(){}

    public Person(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}


