package org.security.app.service;

import org.modelmapper.ModelMapper;
import org.security.app.UserDTO;
import org.security.app.entity.UserEntity;
import org.security.app.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	ModelMapper modelMapper;


	
	public UserDTO saveUser(UserDTO user) throws Exception {
		if(checkUserExists(user.getEmailId())) {
			throw new Exception();
		}		
		UserEntity entity = repository.save(mapDTOtoEntity(user));
		return mapEntityToDTO(entity);
	}
	
	private boolean checkUserExists(String emailId) {
		if(repository.findByEmailId(emailId)!=null) {
			return true;
		}
		return false;
	}
	
	private UserEntity mapDTOtoEntity(UserDTO dto) {
		return modelMapper.map(dto, UserEntity.class);
	}
	
	private UserDTO mapEntityToDTO(UserEntity entity) {
		return modelMapper.map(entity, UserDTO.class);
	}
}
