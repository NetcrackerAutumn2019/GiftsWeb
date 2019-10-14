package com.netcracker.server.service;


import com.netcracker.server.entities.Person;
import com.netcracker.server.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private Repository repository;
    public List<Person> getAll(){
        return this.repository.findAll();
    }

    public void save(Person name) {
        this.repository.save(name);
    }
}
