package com.group5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group5.dto.GetMoviesRequest;
import com.group5.dto.LoginRequest;
import com.group5.dto.LoginResponse;
import com.group5.dto.User;
import com.group5.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	 @Autowired
	 private UserService userService;
	 	
	 	@CrossOrigin(origins = "*")
	    @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@RequestBody User user) {
	        String registeredUser = userService.registerUser(user);
	        return ResponseEntity.ok(registeredUser);
	    }
	    
	    @CrossOrigin(origins = "*")
	    @PostMapping("/login")
	    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
	        System.out.println("Inside Login in controller" + loginRequest.getPassword() + loginRequest.getUsername());
	        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
	        if (token != null) {
	            return ResponseEntity.ok().body(new LoginResponse(token));
	        } else {
	            // Return HTTP status code 401 for Unauthorized
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	        }
	    }
	    
	    @CrossOrigin(origins = "*")
	    @PostMapping("/validateToken")
	    public ResponseEntity<Boolean> validateToken(@RequestBody GetMoviesRequest tokenObj) {
	        System.out.println("Inside validate  Token" + tokenObj.getUserName());
	        boolean success = userService.validateToken(tokenObj);
	        if (success) {
	            return ResponseEntity.ok().body(success);
	        } else {
	            // Return HTTP status code 401 for Unauthorized
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(success);
	        }
	    }

}
