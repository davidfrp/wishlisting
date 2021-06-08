package com.davidfrp.wishlisting.model;

import com.davidfrp.wishlisting.util.SnowflakeGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "username_uindex", columnNames = "username")
        }
)
public class User {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(generator = SnowflakeGenerator.GENERATOR_NAME)
    @GenericGenerator(name = SnowflakeGenerator.GENERATOR_NAME, strategy = "com.davidfrp.wishlisting.util.SnowflakeGenerator")
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "password")
    private String password;

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
}
