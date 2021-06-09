package com.davidfrp.wishlisting.model;

import com.davidfrp.wishlisting.util.SnowflakeGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = SnowflakeGenerator.GENERATOR_NAME)
    @GenericGenerator(name = SnowflakeGenerator.GENERATOR_NAME, strategy = "com.davidfrp.wishlisting.util.SnowflakeGenerator")
    private long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Size(min = 2, message = "Wishlist name must be at least {min} characters long.")
    @Size(max = 32, message = "Wishlist name cannot be longer than {max} characters long.")
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Size(min = 2, message = "The description must be at least {min} characters long.")
    @Size(max = 32, message = "The description cannot be longer than {max} characters long.")
    @Column(name = "description", length = 32, nullable = false)
    private String description;

    @Column(name = "is_private")
    private boolean isPrivate;

    protected Wishlist() { }

    public Wishlist(User author, String name, String description, boolean isPrivate) {
        this.author = author;
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }
}
