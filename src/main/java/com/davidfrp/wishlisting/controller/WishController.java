package com.davidfrp.wishlisting.controller;

import com.davidfrp.wishlisting.model.User;
import com.davidfrp.wishlisting.model.Wish;
import com.davidfrp.wishlisting.model.Wishlist;
import com.davidfrp.wishlisting.service.UserService;
import com.davidfrp.wishlisting.service.WishService;
import com.davidfrp.wishlisting.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WishController {

    private final WishService wishService;
    private final WishlistService wishlistService;
    private final UserService userService;

    @Autowired
    public WishController(WishService wishService, WishlistService wishlistService, UserService userService) {
        this.wishService = wishService;
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping("/wishlists/{id}/add")
    public String createWishlist(@PathVariable("id") long wishlistId, Model model, HttpSession session) {

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        if (userService.getUserFromSession(session) == null)
            return "redirect:/profile/login?redirectTo=/wishlists/" + wishlistId;

        model.addAttribute("wish", new Wish(null, null, null));
        model.addAttribute("wishlist", new Wishlist(null, null, null, true));
        return "createWish";
    }

    @PostMapping("/wishlists/{id}/add")
    public String createWishlist(@PathVariable("id") long wishlistId, @Valid @ModelAttribute("wish") Wish wish,
                                 BindingResult result, HttpSession session) {

        User author = userService.getUserFromSession(session);

        if (author == null)
            return "redirect:/profile/login?redirectTo=/wishlists/" + wishlistId;

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        if (result.hasErrors())
            return "createWish";

        Wish newlyCreatedWish = wishService.createWish(new Wish(wishlist, null, wish.getDescription()));

        if (newlyCreatedWish == null) {
            ObjectError error = new ObjectError("globalError", "Something internally prevented your wish from being added. Try again later.");
            result.addError(error);
            return "createWish";
        }

        return "redirect:/wishlists/" + wishlistId;
    }
}