package com.page2screen.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//User must be logged in to access Profille, Add New, and Edit Title pages
@Controller
public class ViewController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        return "profile";
    }

    @GetMapping("/add-new")
    public String addNew(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        return "add-new";
    }
    
    @GetMapping("/edit-title")
    public String editTitle(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
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