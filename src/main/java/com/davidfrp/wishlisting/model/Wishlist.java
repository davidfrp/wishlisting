package com.davidfrp.wishlisting.model;

import com.davidfrp.wishlisting.util.SnowflakeGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

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

    @Size(min = 2, message = "Navnet på ønskelisten skal være på mindst {min} tegn.")
    @Size(max = 32, message = "Navnet på ønskelisten må ikke være længere end {max} tegn.")
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Size(max = 128, message = "Beskrivelsen må ikke være længere end {max} tegn.")
    @Column(name = "description", length = 128, nullable = false)
    private String description;

    @Column(name = "is_private")
    private boolean isPrivate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wishlist", orphanRemoval = true)
    private List<Wish> wishes;

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

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }
}
