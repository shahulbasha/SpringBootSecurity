package org.security.app.controller;

import org.security.app.UserDTO;
import org.security.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
	
	@Autowired
	AppService service;

	@PostMapping("/signup")
	public UserDTO signup(@RequestBody UserDTO user) throws Exception {
		return service.saveUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody UserDTO user) {
		return "LoggedIn";
	}
	
	@GetMapping("/users")
	public String getUsers() {
		return "User Returned";
	}
}
