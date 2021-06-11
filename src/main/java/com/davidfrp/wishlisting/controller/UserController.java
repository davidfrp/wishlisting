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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    @GetMapping("/profile/logout")
    public String getLoginPage(HttpSession session) {
        session.invalidate();
        return "redirect:/profile/login";
    }

    @GetMapping("/profile/login")
    public String getLoginPage(@RequestParam(name = "redirectTo", required = false) String redirectUrl,
                               HttpSession session, Model model) {

        model.addAttribute("redirectUrl", redirectUrl);
        model.addAttribute("user", new User(null, null, null));
        return "login";
    }

    @PostMapping("/profile/login")
    public String login(@RequestParam(name = "redirectTo", required = false) String redirectUrl,
                        @ModelAttribute("user") User user,
                        BindingResult result,
                        HttpSession session) {

        if (result.hasErrors())
            return "login";

        if (!userService.doesUsernameAndPasswordMatch(user.getUsername(), user.getPassword())) {
            ObjectError error = new ObjectError("globalError", "Kontroller, at du har brugt det rigtige brugernavn med den rette adgangskode.");
            result.addError(error);
            return "login";
        }

        User loggedInUser = userService.getUserByUsername(user.getUsername());

        session.setAttribute("user_id", loggedInUser.getId());
        session.setAttribute("user_display_name", loggedInUser.getDisplayName());

        if (redirectUrl.isBlank())
            return "redirect:/profile/my-wishlists";

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/profile/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User(null, null, null));
        return "signup";
    }

    @PostMapping("/profile/signup")
    public String signupPage(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {

        if (result.hasErrors())
            return "signup";

        if (!userService.isUsernameAvailable(user.getUsername())) {
            result.rejectValue("username", "error.user", "Der findes allerede en profil med dette brugernavn.");
            return "signup";
        }

        User newlyRegistredUser = userService.registerUser(user);

        if (newlyRegistredUser == null) {
            ObjectError error = new ObjectError("globalError", "En intern fejl forhindrede din profil i at blive oprettet. Prøv igen senere.");
            result.addError(error);
            return "signup";
        }

        session.setAttribute("user_id", newlyRegistredUser.getId());
        session.setAttribute("user_display_name", newlyRegistredUser.getDisplayName());
        return "redirect:/profile/my-wishlists";
    }
}
