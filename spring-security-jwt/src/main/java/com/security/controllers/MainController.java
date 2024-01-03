package com.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.security.models.AuthRequest;
import com.security.models.Roles;
import com.security.models.User;
import com.security.services.JwtService;
import com.security.services.MainService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("userJwt")
public class MainController {

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private MainService service;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("testMethod")
	public String testMethod() {
		return "this test method is not secured....";
	}

	@PostMapping("create/user")
	public String createUser(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		service.createUser(user);
		return "User is created with UserId : " + user.getUserId();
	}

	@PostMapping("create/role")
	public String createRole(@RequestBody Roles role) {
		service.createRole(role);
		return "Role is created for User : " + role.getUser().getUserId();
	}

	/*
	This generateToken api is used to generate JWT Token by taking AuthRequest parameter(contains
	username and password)
	First this method authenticate user that it present in the database or not, If yes then generate token
	otherwise return user not found exception.
	This method authenticates user by using AuthenticationManager interface.
	*/
	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}
	
	@GetMapping("getUser/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username){
		User user = service.getUser(username);
		if(user == null) return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.FOUND).body(user);
	}

	@GetMapping("getAllUsers")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> list = service.getAll();
		if(list.isEmpty()) return new ResponseEntity("List is empty", HttpStatus.NO_CONTENT);
		return ResponseEntity.status(HttpStatus.FOUND).body(list);
	}
}
