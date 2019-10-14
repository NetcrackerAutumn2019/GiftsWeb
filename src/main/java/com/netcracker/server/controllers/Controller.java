package com.netcracker.server.controllers;

import com.netcracker.server.entities.Person;
import com.netcracker.server.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private Service service;
    @RequestMapping(value="/helo", method = RequestMethod.GET)
    public String helloWorld (){
        return "Hello World!";
    }
    @RequestMapping(value="/persons", method = RequestMethod.GET)
    public List<Person> getPersons(){
        return this.service.getAll();
    }
    @RequestMapping(value="/create", method = RequestMethod.GET)
    public void createPerson (){
        this.service.save(new Person("Uran")); // т.к. у нас еще не реализована регистрация через фронт, посмотрим работает ли сохранения в бд так
    }
}
