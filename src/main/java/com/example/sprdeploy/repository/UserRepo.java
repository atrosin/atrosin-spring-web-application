package com.example.sprdeploy.repository;

import com.example.sprdeploy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
        User findByUsername(String username);
}
