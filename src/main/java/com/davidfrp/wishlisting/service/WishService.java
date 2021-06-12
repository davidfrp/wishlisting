package com.davidfrp.wishlisting.service;

import com.davidfrp.wishlisting.model.User;
import com.davidfrp.wishlisting.model.Wish;
import com.davidfrp.wishlisting.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public Wish saveWish(Wish wish) {
        return wishRepository.save(wish);
    }

    public Wish getWishById(long id) {
        return wishRepository.findById(id).orElse(null);
    }

    public void reserveWish(Wish wish, User appointee) {
        wish.setAppointee(appointee);
        wishRepository.save(wish);
    }

    public void unreserveWish(Wish wish, User appointee) {
        wish.setAppointee(null);
        wishRepository.save(wish);
    }

    public void deleteWish(Wish wish) {
        wishRepository.delete(wish);
    }
}
