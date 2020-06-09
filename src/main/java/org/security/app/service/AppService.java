package org.security.app.service;

import org.modelmapper.ModelMapper;
import org.security.app.entity.UserEntity;
import org.security.app.entity.UserRepository;
import org.security.app.model.AuthenticationResponseModel;
import org.security.app.model.SignUpRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	
	public AuthenticationResponseModel saveUser(SignUpRequestModel signUpModel) throws Exception {
		if(checkUserExists(signUpModel.getEmail())) {
			throw new Exception("User Already Exists");
		}
		signUpModel.setPassword(passwordEncoder.encode(signUpModel.getPassword()));
		UserEntity entity = repository.save(mapToEntity(signUpModel));
		
 
        
		return mapEntityToDTO(entity);
	}
	
	private boolean checkUserExists(String emailId) {
		if(repository.findByEmail(emailId)!=null) {
			return true;
		}
		return false;
	}
	
	private UserEntity mapToEntity(SignUpRequestModel signUpModel) {
		return modelMapper.map(signUpModel, UserEntity.class);
	}
	
	private AuthenticationResponseModel mapEntityToDTO(UserEntity entity) {
		return modelMapper.map(entity, AuthenticationResponseModel.class);
	}
}
