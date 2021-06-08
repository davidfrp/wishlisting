package com.davidfrp.wishlisting.controller;

import com.davidfrp.wishlisting.model.User;
import com.davidfrp.wishlisting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public String login(@ModelAttribute("user") User user, BindingResult result, HttpSession session) {

        if (result.hasErrors())
            return "login";

        if (!userService.doesUsernameAndPasswordMatch(user.getUsername(), user.getPassword())) {
            ObjectError error = new ObjectError("globalError", "Please check to make sure you used the right username and password.");
            result.addError(error);
            return "login";
        }

        User loggedInUser = userService.getUserByUsername(user.getUsername());

        session.setAttribute("user_id", loggedInUser.getId());
        return "redirect:/profile";
    }

    @GetMapping("/profile/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User(null, null, null));
        return "signup";
    }

    @PostMapping("/profile/signup")
    public String signupPage(@ModelAttribute("user") User user, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors())
            return "signup";

        if (!userService.isUsernameAvailable(user.getUsername())) {
            result.rejectValue("username", "error.user", "A profile with this username already exists.");
            return "signup";
        }

        User newlyRegistredUser = userService.registerUser(user.getDisplayName(), user.getUsername(), user.getPassword());

        if (newlyRegistredUser == null) {
            ObjectError error = new ObjectError("globalError", "Something internally prevented your profile from being created. Try again later.");
            result.addError(error);
            return "signup";
        }

        session.setAttribute("user_id", newlyRegistredUser.getId());
        return "redirect:/profile";
    }
}
