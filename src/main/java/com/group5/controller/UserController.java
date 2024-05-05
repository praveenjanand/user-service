package com.group5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group5.dto.LoginRequest;
import com.group5.dto.User;
import com.group5.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	 @Autowired
	 private UserService userService;

	    @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@RequestBody User user) {
	        String registeredUser = userService.registerUser(user);
	        return ResponseEntity.ok(registeredUser);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
	        System.out.println("Inside Login in controller"+loginRequest.getPassword()+loginRequest.getUsername());
	        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
	        if (token != null) {
	            return ResponseEntity.ok(token);
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	        }
	    }

}
