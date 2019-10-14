package com.test.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.test.entity.Users;
import com.test.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class HelloController {

    @Autowired
    public UsersService usersService;

    @GetMapping("write/{name}")
    public String write(@PathVariable String name) {
        Users users = new Users(name);
        usersService.save(users);
        return "write";
    }

    @GetMapping("read/{id}")
    public String read(@PathVariable String id, Model model){
        System.out.println(id);
        Optional<Users> u = usersService.findById(Long.parseLong(id));
        Users user = usersService.findById(Long.parseLong(id)).get();
        model.addAttribute("user", user);
        //model.addAttribute("id", user.getId());
        //model.addAttribute("name", user.getName());
        return "read";
    }
}
