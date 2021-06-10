package com.davidfrp.wishlisting.service;

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

    public Wish createWish(Wish wish) {
        return wishRepository.save(wish);
    }
}
