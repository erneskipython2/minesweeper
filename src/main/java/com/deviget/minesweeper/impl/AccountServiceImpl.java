package com.deviget.minesweeper.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public boolean createAccount(String username, String password) {
		String enc = encoderHelper(password);
		UserDao user = new UserDao();
		user.setUsername(username);
		user.setPassword(enc);
		user.setRoles(Arrays.asList(Role.PLAYER.toString()));
		rep.save(user);
		log.info("Created account user: {} pass: {} encpass: {}", username, password, enc);
		return true;
		
	}
	
	
	private String encoderHelper(String password) {
		return bcryptEncoder.encode(password);
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
