package com.example.demo.model;

public class AuthenticanResponse {
  private  String jwt ;

public AuthenticanResponse() {
	super();
}

public AuthenticanResponse(String jwt) {
	super();
	this.jwt = jwt;
}

public String getJwt() {
	return jwt;
}
  
}
