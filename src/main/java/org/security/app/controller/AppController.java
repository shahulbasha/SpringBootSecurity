package org.security.app.controller;

import org.security.app.model.AuthenticationResponseModel;
import org.security.app.model.SignUpRequestModel;
import org.security.app.service.AppService;
import org.security.app.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class AppController {
	
	@Autowired
	AppService service;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignUpRequestModel signUpModel) throws Exception {
		 AuthenticationResponseModel responseModel = service.saveUser(signUpModel);
		 //ResponseEntity<AuthenticationResponseModel> responseEntity=new ResponseEntity<AuthenticationResponseModel>(HttpStatus.OK);
		// responseEntity.getHeaders().set("token", JWTUtil.generateToken(responseModel.getEmailId()));
		 HttpHeaders headers=new HttpHeaders();
		 headers.add("token", JWTUtil.generateToken(responseModel.getEmail()));
		 return new ResponseEntity<String>("Authentication is Successful",headers,HttpStatus.OK);	 
	}
	

	
	@GetMapping("/users")
	public String getUsers() {
		return "User Returned";
	}
}
