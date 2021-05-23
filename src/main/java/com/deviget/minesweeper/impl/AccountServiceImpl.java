package com.deviget.minesweeper.impl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.Role;
import com.deviget.minesweeper.domain.UserDao;
import com.deviget.minesweeper.repository.UserRepository;
import com.deviget.minesweeper.service.AccountService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	UserRepository rep;
	
	@Override
	public String createAccount(String username, String password) {
		String enc = encoderHelper(password);
		UserDao user = new UserDao();
		user.setUsername(username);
		user.setPassword(enc);
		user.setRoles(Arrays.asList(Role.PLAYER.toString()));
		Optional<UserDao> current = rep.findByUsername(username);
		String id = "";
		if(current.isPresent()) {
			UserDao temp = current.get();
			temp.setPassword(enc);
			id = rep.save(temp).getId();
		}else {
			id = rep.save(user).getId();
		}
		
		log.info("Created account user: {} pass: {} encpass: {} id: {}", username, password, enc, id);
		return id;
		
	}
	
	
	private String encoderHelper(String password) {
		return bcryptEncoder.encode(password);
		
	}

}
