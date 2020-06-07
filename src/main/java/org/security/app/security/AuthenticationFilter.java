package org.security.app.security;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.security.app.UserDTO;
import org.security.app.model.AuthenticationResponseModel;
import org.security.app.model.LoginRequestModel;
import org.security.app.service.UserService;
import org.security.app.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	UserService usersService;
	@Autowired
	ModelMapper mapper;
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
    	
    	String token=JWTUtil.generateToken(userDetails.getEmail());
    	AuthenticationResponseModel authenticationResponseModel = mapper.map(userDetails, AuthenticationResponseModel.class);
    	res.getWriter().write(new ObjectMapper().writeValueAsString(authenticationResponseModel));
    	res.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	res.setCharacterEncoding("UTF-8");
        res.addHeader("token", token); 
    }

}
