package com.page2screen.web;

import com.page2screen.config.DataSeedService;
import com.page2screen.repo.ReviewRepository;
import com.page2screen.repo.WorkRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Profile("local")
public class AdminController {
  private final DataSeedService dataSeedService;
  private final WorkRepository workRepository;
  private final ReviewRepository reviewRepository;

  public AdminController(DataSeedService dataSeedService, WorkRepository workRepository, ReviewRepository reviewRepository) {
    this.dataSeedService = dataSeedService;
    this.workRepository = workRepository;
    this.reviewRepository = reviewRepository;
  }

  @GetMapping("/admin")
  public String admin(Model model) {
    model.addAttribute("worksCount", workRepository.count());
    model.addAttribute("reviewsCount", reviewRepository.count());
    return "admin";
  }

  @PostMapping("/admin/clear")
  public String clear(RedirectAttributes ra) {
    dataSeedService.clearAll();
    ra.addFlashAttribute("message", "Database cleared.");
    return "redirect:/admin";
  }

  @PostMapping("/admin/seed")
  public String seed(RedirectAttributes ra) {
    dataSeedService.seedAll();
    ra.addFlashAttribute("message", "Database seeded.");
    return "redirect:/admin";
  }
}
