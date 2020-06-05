package org.security.app.security;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.security.app.UserDTO;
import org.security.app.model.LoginRequestModel;
import org.security.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	UserService usersService;
	public AuthenticationFilter(UserService usersService,AuthenticationManager authManager) {
		super();
		this.usersService = usersService;
		super.setAuthenticationManager(authManager);
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequestModel loginModel = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmailId(), loginModel.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    	String userName = ((User) auth.getPrincipal()).getUsername();
    	UserDTO userDetails = usersService.getUserDetailsByEmail(userName);
    	
        String token = Jwts.builder()
                .setSubject(userDetails.getEmailId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("1000")))
                .signWith(SignatureAlgorithm.HS512, "secretToken" )
                .compact();
        
        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getEmailId());
    }

}
