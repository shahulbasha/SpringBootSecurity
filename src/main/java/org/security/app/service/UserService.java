package org.security.app.service;

import org.security.app.entity.UserEntity;
import org.security.app.entity.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		UserEntity userEntity = repository.findByEmailId(emailId);
		if(userEntity==null) {
			throw new UsernameNotFoundException("Username Not Found");
		}
		return new User(userEntity.getFirstName(), userEntity.getPassword(), null);
	}

	

}
