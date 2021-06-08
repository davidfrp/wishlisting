package com.davidfrp.wishlisting.controller;

import com.davidfrp.wishlisting.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping
    public String getIndex() {
        return "redirect:/profile/login";
    }

    @GetMapping("/profile/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new User(null, null, null));
        return "login";
    }

    @PostMapping("/profile/login")
    public String login(@ModelAttribute("user") User user) {
        return "redirect:/profile";
    }
}
