package org.security.app.controller;

import org.security.app.model.AuthenticationResponseModel;
import org.security.app.model.SignUpRequestModel;
import org.security.app.service.AppService;
import org.security.app.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AppController {
	
	@Autowired
	AppService service;

	@CrossOrigin(exposedHeaders = "token")
	@PostMapping("/signup")
	public ResponseEntity<AuthenticationResponseModel> signup(@RequestBody SignUpRequestModel signUpModel) throws Exception {
		 AuthenticationResponseModel responseModel = service.saveUser(signUpModel);
		 HttpHeaders headers=new HttpHeaders();
		 headers.add("token", JWTUtil.generateToken(responseModel.getEmail()));
		 return ResponseEntity.ok().headers(headers).body(responseModel);	 
	}
	

	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "Your dahsboard doesnt have any stories to show";
	}
}
