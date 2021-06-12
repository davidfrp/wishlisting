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

    @GetMapping("/profile/reserved-wishes")
    public String getWishlistsPage(Model model, HttpSession session ) {

        User user = userService.getUserFromSession(session);

        if (user == null)
            return "redirect:/profile/login";

        model.addAttribute("reservedWishes", user.getReservedWishes());
        return "reservedWishes";
    }

    @GetMapping("/wishlist/{id}/add")
    public String createWish(@PathVariable("id") long wishlistId, Model model, HttpSession session) {

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        User author = userService.getUserFromSession(session);

        if (author == null)
            return "redirect:/profile/login?redirectTo=/wishlist/" + wishlistId;

        if (author.getId() != wishlist.getAuthor().getId())
            return "error/403";

        model.addAttribute("wish", new Wish(null, null, null));
        model.addAttribute("wishlist", wishlist);
        return "createWish";
    }

    @PostMapping("/wishlist/{id}/add")
    public String createWish(@PathVariable("id") long wishlistId, @Valid @ModelAttribute("wish") Wish wish,
                             BindingResult result, HttpSession session, Model model) {

        User author = userService.getUserFromSession(session);

        if (author == null)
            return "redirect:/profile/login?redirectTo=/wishlist/" + wishlistId;

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        if (author.getId() != wishlist.getAuthor().getId())
            return "error/403";

        if (result.hasErrors()) {
            model.addAttribute("wishlist", wishlist);
            return "createWish";
        }

        Wish newlyCreatedWish = wishService.saveWish(new Wish(wishlist, null, wish.getDescription()));

        if (newlyCreatedWish == null) {
            ObjectError error = new ObjectError("globalError", "Something internally prevented your wish from being added. Try again later.");
            result.addError(error);
            return "createWish";
        }

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/wishlist/{wishlistId}/{wishId}/edit")
    public String editWish(@PathVariable("wishlistId") long wishlistId,
                           @PathVariable("wishId") long wishId,
                           Model model, HttpSession session) {

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        User author = userService.getUserFromSession(session);

        if (author == null)
            return "redirect:/profile/login?redirectTo=/wishlist/" + wishlistId;

        Wish wish = wishService.getWishById(wishId);

        if (wish == null)
            return "error/404";

        if (author.getId() != wishlist.getAuthor().getId())
            return "error/403";

        model.addAttribute("wish", wish);
        model.addAttribute("wishlist", wishlist);
        return "editWish";
    }

    @PostMapping("/wishlist/{wishlistId}/{wishId}/edit")
    public String editWish(@PathVariable("wishlistId") long wishlistId,
                           @PathVariable("wishId") long wishId, 
                           @Valid @ModelAttribute("wish") Wish editedWish,
                           BindingResult result, HttpSession session, Model model) {

        User author = userService.getUserFromSession(session);

        if (author == null)
            return "redirect:/profile/login?redirectTo=/wishlist/" + wishlistId;

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        if (author.getId() != wishlist.getAuthor().getId())
            return "error/403";

        if (result.hasErrors()) {
            model.addAttribute("wish", editedWish);
            model.addAttribute("wishlist", wishlist);
            return "editWish";
        }

        Wish wish = wishService.getWishById(wishId);

        if (wish == null)
            return "error/404";

        wish.setAppointee(wish.getAppointee());
        wish.setWishlist(wishlist);
        wish.setDescription(editedWish.getDescription());

        Wish newlyEditedWish = wishService.saveWish(wish);

        if (newlyEditedWish == null) {
            ObjectError error = new ObjectError("globalError", "Something internally prevented your wish from being added. Try again later.");
            result.addError(error);
            return "editWish";
        }

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/wishlist/{wishlistId}/{wishId}/reserve")
    public String reserveWish(@PathVariable("wishlistId") long wishlistId,
                              @PathVariable("wishId") long wishId,
                              HttpSession session) {

        User appointee = userService.getUserFromSession(session);

        if (appointee == null)
            return "redirect:/profile/login?redirectTo=/wishlist/" + wishlistId;

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        Wish wish = wishService.getWishById(wishId);

        if (wish == null)
            return "error/404";

        if (appointee.getId() == wishlist.getAuthor().getId())
            return "error/403";

        wishService.reserveWish(wish, appointee);

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/wishlist/{wishlistId}/{wishId}/unreserve")
    public String unreserveWish(@PathVariable("wishlistId") long wishlistId,
                                @PathVariable("wishId") long wishId,
                                HttpSession session) {

        User appointee = userService.getUserFromSession(session);

        if (appointee == null)
            return "redirect:/profile/login?redirectTo=/wishlist/" + wishlistId;

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        Wish wish = wishService.getWishById(wishId);

        if (wish == null)
            return "error/404";

        if (appointee.getId() == wishlist.getAuthor().getId())
            return "error/403";

        if (wish.getAppointee() != null && wish.getAppointee().getId() == appointee.getId())
            wishService.unreserveWish(wish, appointee);

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/wishlist/{wishlistId}/{wishId}/delete")
    public String deleteWish(@PathVariable("wishlistId") long wishlistId,
                                @PathVariable("wishId") long wishId,
                                HttpSession session) {

        User author = userService.getUserFromSession(session);

        if (author == null)
            return "redirect:/profile/login?redirectTo=/wishlist/" + wishlistId;

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);

        if (wishlist == null)
            return "error/404";

        Wish wish = wishService.getWishById(wishId);

        if (wish == null)
            return "error/404";

        if (author.getId() == wishlist.getAuthor().getId())
            wishService.deleteWish(wish);

        return "redirect:/wishlist/" + wishlistId;
    }
}
