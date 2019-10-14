package com.netcracker.server.repositories;


import com.netcracker.server.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository <Person,Integer> {

}
