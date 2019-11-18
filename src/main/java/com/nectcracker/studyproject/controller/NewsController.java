package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.News;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.service.NewsService;
import com.nectcracker.studyproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
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
    public String news(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        Map<String, Set<News>> news = newsService.findByUser(user);
        model.addAttribute("newNews", news.get("newNews"));
        model.addAttribute("oldNews", news.get("oldNews"));
        return "news";
    }
}
