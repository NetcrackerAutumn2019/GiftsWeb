package com.test.service;

import com.test.entity.Users;
import com.test.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public List<Users> findAll(){
        return usersRepository.findAll();
    }

    public Optional<Users> findById(Long id){
        return usersRepository.findById(id);
    }

    public void save(Users users){
        usersRepository.save(users);
    }

}
