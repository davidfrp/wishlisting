package com.davidfrp.wishlisting.model;

import com.davidfrp.wishlisting.util.SnowflakeGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "wishes")
public class Wish {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = SnowflakeGenerator.GENERATOR_NAME)
    @GenericGenerator(name = SnowflakeGenerator.GENERATOR_NAME, strategy = "com.davidfrp.wishlisting.util.SnowflakeGenerator")
    private long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "appointee_id")
    private User appointee;

    @Size(min = 2, message = "Beskrivelsen skal være på mindst {min} tegn.")
    @Size(max = 64, message = "Beskrivelsen må ikke være længere end {max} tegn.")
    @Column(name = "description", length = 64, nullable = false)
    private String description;

    protected Wish() { }

    public Wish(Wishlist wishlist, User appointee, String description) {
        this.wishlist = wishlist;
        this.appointee = appointee;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public User getAppointee() {
        return appointee;
    }

    public String getDescription() {
        return description;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public void setAppointee(User appointee) {
        this.appointee = appointee;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
