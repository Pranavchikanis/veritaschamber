package com.veritaschambers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @GetMapping({"", "/", "/login"})
    public String login() {
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/consultations")
    public String consultations() {
        return "admin/consultations";
    }

    @GetMapping("/contact-messages")
    public String contactMessages() {
        return "admin/contact-messages";
    }

    @GetMapping("/practice-areas")
    public String practiceAreas() {
        return "admin/practice-areas";
    }

    @GetMapping("/articles")
    public String articles() {
        return "admin/articles";
    }

    @GetMapping("/faqs")
    public String faqs() {
        return "admin/faqs";
    }

    @GetMapping("/settings")
    public String settings() {
        return "admin/settings";
    }
}
