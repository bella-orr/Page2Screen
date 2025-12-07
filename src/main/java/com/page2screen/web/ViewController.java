package com.page2screen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ViewController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/add-new")
    public String addNew() {
        return "add-new";
    }
    
    @GetMapping("/edit-title")
    public String editTitle() {
        return "edit-title";
    }
    
    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    @GetMapping("/title-detail")
    public String titleDetail() {
        return "title-detail";
    }
}
