package com.davidfrp.wishlisting.service;

import com.davidfrp.wishlisting.model.Wishlist;
import com.davidfrp.wishlisting.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist getWishlistById(long id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public void deleteWishlist(Wishlist wishlist) {
        wishlistRepository.delete(wishlist);
    }
}
