package com.veritaschambers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "public/home";
    }

    @GetMapping("/about")
    public String about() {
        return "public/about";
    }

    @GetMapping("/practice-areas")
    public String practiceAreas() {
        return "public/practice-areas/index";
    }

    @GetMapping("/practice-areas/{slug}")
    public String practiceAreaDetail(@PathVariable String slug, Model model) {
        model.addAttribute("slug", slug);
        return "public/practice-areas/detail";
    }

    @GetMapping("/legal-insights")
    public String legalInsights() {
        return "public/legal-insights/index";
    }

    @GetMapping("/legal-insights/{slug}")
    public String articleDetail(@PathVariable String slug, Model model) {
        model.addAttribute("slug", slug);
        return "public/legal-insights/detail";
    }

    @GetMapping("/faq")
    public String faq() {
        return "public/faq";
    }

    @GetMapping("/contact")
    public String contact() {
        return "public/contact";
    }

    @GetMapping("/legal/{slug}")
    public String legalPage(@PathVariable String slug, Model model) {
        model.addAttribute("slug", slug);
        return "public/legal";
    }
}
