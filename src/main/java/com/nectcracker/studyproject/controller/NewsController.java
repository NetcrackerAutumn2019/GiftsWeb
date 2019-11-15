package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.News;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.service.NewsService;
import com.nectcracker.studyproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;

    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/news")
    public String news(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        Set<News> news = newsService.findByUser(user);
        return "news";
    }
}
