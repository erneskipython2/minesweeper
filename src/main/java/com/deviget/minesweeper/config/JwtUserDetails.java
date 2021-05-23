package com.deviget.minesweeper.config;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.UserDao;
import com.deviget.minesweeper.repository.UserRepository;


/**
 * Service class for user details management
 *
 */
@Service
public class JwtUserDetails implements UserDetailsService {
	
	@Autowired
	UserRepository rep;

	/**
	 * Find the user details from database
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		Optional<UserDao> user = rep.findByUsername(username);
		if(user.isPresent()){
			return user.get();
		}

		throw new UsernameNotFoundException("Username: " + username + " not found");

	}

}
