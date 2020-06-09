package org.security.app.model;

public class LoginRequestModel {

	private String email;
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String emailId) {
		this.email = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
