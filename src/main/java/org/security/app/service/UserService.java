package org.security.app.service;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.security.app.UserDTO;
import org.security.app.entity.UserEntity;
import org.security.app.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository repository;
	@Autowired
	ModelMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		UserEntity userEntity = repository.findByEmailId(emailId);
		if(userEntity==null) {
			throw new UsernameNotFoundException("Username Not Found");
		}
		return new User(userEntity.getEmailId(), userEntity.getPassword(), new ArrayList<>());
	}


	public UserDTO getUserDetailsByEmail(String emailId) throws UsernameNotFoundException {
		UserEntity userEntity = repository.findByEmailId(emailId);
		if(userEntity==null) {
			throw new UsernameNotFoundException("Username Not Found");
		}
		return mapper.map(userEntity, UserDTO.class);
	}

}
