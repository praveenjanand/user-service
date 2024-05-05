package com.group5.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Session")
public class Session {
	
    @Id
    private String id;
    private String username;
    private String token;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Session(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}
	@Override
	public String toString() {
		return "Session [id=" + id + ", username=" + username + ", token=" + token + "]";
	}
    
    


}
