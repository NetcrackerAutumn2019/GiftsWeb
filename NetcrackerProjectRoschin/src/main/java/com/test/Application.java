package com.test;



import com.test.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
//@EnableJpaAuditing
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}