package com.example.demo.model;

public class AuthenticationRequest {
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	private String username;
	private String userpassword;
	public AuthenticationRequest(String username, String userpassword) {
		super();
		this.username = username;
		this.userpassword = userpassword;
	}
	public AuthenticationRequest() {
		super();
	}
	

}
