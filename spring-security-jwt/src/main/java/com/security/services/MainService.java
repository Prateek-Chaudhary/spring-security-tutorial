package com.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.models.Roles;
import com.security.models.User;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MainService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;	

	public void createUser(User user) {
		userRepo.save(user);
	}

	public void createRole(Roles role) {
		roleRepo.save(role);
	}

	public User getUser(String username) {
		return userRepo.findByUsername(username);
	}


	public List<User> getAll() {
		return userRepo.findAll();
	}
}
