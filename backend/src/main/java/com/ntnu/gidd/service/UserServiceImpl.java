package com.ntnu.gidd.service;

import com.ntnu.gidd.dto.UserDto;
import com.ntnu.gidd.dto.UserRegistrationDto;
import com.ntnu.gidd.exception.EmailInUseException;
import com.ntnu.gidd.model.User;
import com.ntnu.gidd.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@NoArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDto updateUser(UUID id, User activity) {
		return null;
	}
	
	@Override
	public UserDto getUserById(UUID id) {
		return null;
	}

	private boolean emailExist(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
	
	@Override
	public UserDto saveUser(UserRegistrationDto user) {
userRepository.findbyEmail(email).orElseThrow(EmailInUseException::new);

			throw new EmailInUseException();
		}

		//Hash and salt
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User userObj = modelMapper.map(user, User.class);
		return modelMapper.map(userRepository.save(userObj), UserDto.class);
	}
	
	@Override
	public UserDto deleteUser(UUID id) {
		return null;
	}
}
