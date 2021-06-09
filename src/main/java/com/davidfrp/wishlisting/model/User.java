package com.davidfrp.wishlisting.model;

import com.davidfrp.wishlisting.util.SnowflakeGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @Size(min = 2, max = 32, message = "Username must be at least {min} characters long.")
    @Column(name = "username", length = 32, nullable = false)
    private String username;

    @Size(min = 2, max = 32, message = "Display name must be at least {min} characters long.")
    @Column(name = "display_name", length = 32, nullable = false)
    private String displayName;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Use 8 or more characters with a mix of letters, numbers & symbols.")
    @Size(max = 128, message = "Password cannot be longer than {max} characters long.")
    @Column(name = "password", length = 128, nullable = false)
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
