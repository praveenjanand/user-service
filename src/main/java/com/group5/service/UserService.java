package com.group5.service;

import java.security.GeneralSecurityException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group5.dto.GetMoviesRequest;
import com.group5.dto.Session;
import com.group5.dto.User;
import com.group5.repo.SessionRepository;
import com.group5.repo.UserRepository;
import com.group5.util.Base64EncodingUtil;
import com.group5.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	SessionRepository sessionRepository;


    public String registerUser(User user) throws GeneralSecurityException {
        user.setPassword(Base64EncodingUtil.encrypt(user.getPassword())); 
        userRepository.save(user);
        return "Registration Successful. Welcome "+user.getUsername();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String login(String username, String password) throws GeneralSecurityException {
        System.out.println("Inside Login in service");
        User user = userRepository.findByUsername(username);
        System.out.println("User retrieved from MongoDB"+user);
        String encryptedPassword = Base64EncodingUtil.decrypt(user.getPassword());
            if (user != null && password.equals(encryptedPassword)) {
            	String token = JwtUtil.generateJwtToken(username);
            	Session sessionObj = sessionRepository.findByUsername(username);
            	if(null!=sessionObj) {
            		sessionObj.setToken(token);
            	}else {
            		sessionObj= new Session(username,token);
            	}
            	sessionRepository.save(sessionObj);
                return token;
            }
        return null; // Login failed
    }

	public boolean validateToken(GetMoviesRequest tokenObj) {
        Session session = sessionRepository.findByUsername(tokenObj.getUserName());
        return session.getToken().equalsIgnoreCase(tokenObj.getToken());

	}


}
