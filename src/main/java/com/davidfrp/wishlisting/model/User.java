package com.davidfrp.wishlisting.model;

import com.davidfrp.wishlisting.util.SnowflakeGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "username_uindex", columnNames = "username")
        }
)
public class User {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = SnowflakeGenerator.GENERATOR_NAME)
    @GenericGenerator(name = SnowflakeGenerator.GENERATOR_NAME, strategy = "com.davidfrp.wishlisting.util.SnowflakeGenerator")
    private long id;

    @Size(min = 2, message = "Brugernavnet skal være på mindst {min} tegn.")
    @Size(max = 32, message = "Brugernavnet må ikke være længere end {max} tegn.")
    @Column(name = "username", length = 32, nullable = false)
    private String username;

    @Size(min = 2, message = "Dit navn skal være på mindst {min} tegn.")
    @Size(max = 32, message = "Dit navn må ikke være længere end {max} tegn.")
    @Column(name = "display_name", length = 32, nullable = false)
    private String displayName;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Brug mindst 8 tegn i en kombination af bogstaver, numre & symboler.")
    @Size(max = 128, message = "Adgangskoden må ikke være længere end {max} tegn.")
    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", orphanRemoval = true)
    private List<Wishlist> wishlists;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "appointee")
    private List<Wish> reservedWishes;

    protected User() { }

    public User(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public List<Wish> getReservedWishes() {
        return reservedWishes;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PreRemove
    private void preRemove() {
        reservedWishes.forEach(reservedWish -> reservedWish.setAppointee(null));
    }
}
