package com.davidfrp.wishlisting.repository;

import com.davidfrp.wishlisting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
