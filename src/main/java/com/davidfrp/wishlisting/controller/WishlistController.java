package com.davidfrp.wishlisting.controller;

import com.davidfrp.wishlisting.model.User;
import com.davidfrp.wishlisting.model.Wishlist;
import com.davidfrp.wishlisting.service.UserService;
import com.davidfrp.wishlisting.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;

    @Autowired
    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping("/profile/my-wishlists")
    public String getWishlistsPage(Model model, HttpSession session ) {

        User user = userService.getUserFromSession(session);

        if (user == null)
            return "redirect:/profile/login";

        model.addAttribute("wishlists", user.getWishlists());
        return "myWishlists";
    }

    @GetMapping("/profile/my-wishlists/add")
    public String createWishlist(Model model, HttpSession session) {

        if (userService.getUserFromSession(session) == null)
            return "redirect:/profile/login";

        model.addAttribute("wishlist", new Wishlist(null, null, null, true));
        return "createWishlist";
    }

    @PostMapping("/profile/my-wishlists/add")
    public String createWishlist(@Valid @ModelAttribute("wishlist") Wishlist wishlist, BindingResult result, HttpSession session) {

        User author = userService.getUserFromSession(session);

        if (author == null)
            return "redirect:/profile/login";

        if (result.hasErrors())
            return "createWishlist";

        Wishlist newlyCreatedWishlist = wishlistService.createWishlist(
                new Wishlist(
                        author,
                        wishlist.getName(),
                        wishlist.getDescription(),
                        wishlist.getIsPrivate()));

        if (newlyCreatedWishlist == null) {
            ObjectError error = new ObjectError("globalError", "Something internally prevented your wishlist from being created. Try again later.");
            result.addError(error);
            return "createWishlist";
        }

        return "redirect:/wishlist/" + wishlist.getId();
    }

    @GetMapping("/wishlist/{id}")
    public String getWishlistsPage(@PathVariable("id") long wishlistId, Model model, HttpSession session) {

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        User loggedInUser = userService.getUserFromSession(session);

        if (wishlist.getIsPrivate()) {

            if (loggedInUser == null)
                return "redirect:/profile/login?redirectTo=/wishlist/" + wishlist.getId();

            if (loggedInUser.getId() != wishlist.getAuthor().getId())
                return "error/403";
        }

        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }

    @PutMapping("/wishlist/{id}")
    public String editWishlist(@PathVariable("id") long wishlistId, HttpSession session) {
        return "";
    }

    @DeleteMapping("/wishlist/{id}")
    public String deleteWishlist(@PathVariable("id") long wishlistId, HttpSession session) {
        return "";
    }
}
