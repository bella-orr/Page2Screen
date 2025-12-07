package com.page2screen.web;

import jakarta.servlet.http.HttpSession;
import com.page2screen.domain.MediaType;
import com.page2screen.domain.Work;
import com.page2screen.repo.WorkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
//User must be logged in to access Profille, Add New, and Edit Title pages
@Controller
public class ViewController {
    private final WorkRepository workRepository;

    public ViewController(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

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

    @GetMapping("/title/{workId}")
    public String titleDetailForWork(@PathVariable() java.util.UUID workId, Model model) {
        Work work = workRepository.findById(workId).orElse(null);
        model.addAttribute("work", work);
        return "title-detail";
    }

    @GetMapping("/books")
    public String books(Model model) {
        List<Work> books = workRepository.findAll().stream()
                .filter(w -> w.getMediaType() == MediaType.BOOK)
                .toList();
        model.addAttribute("works", books);
        return "books";
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        List<Work> movies = workRepository.findAll().stream()
                .filter(w -> w.getMediaType() == MediaType.MOVIE)
                .toList();
        model.addAttribute("works", movies);
        return "movies";
    }
}