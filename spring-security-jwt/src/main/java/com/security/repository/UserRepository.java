package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.security.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
}
