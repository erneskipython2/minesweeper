package com.deviget.minesweeper.controller;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deviget.minesweeper.config.JwtTokenUtil;
import com.deviget.minesweeper.domain.ErrorTypes;
import com.deviget.minesweeper.domain.JwtRequest;
import com.deviget.minesweeper.domain.JwtResponse;
import com.deviget.minesweeper.exception.MineSweeperException;


/**
 * Authentication Controller with JWT
 * @author  Erneski Coronado
 *
 */
@RestController
@RequestMapping("${endpoint.root}${endpoint.v1}")
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	/**
	 * Request for authentication token for access endpoints by profile
	 * @param JwtRequest
	 * @return The Bearer token if credentials are validated
	 */
	@PostMapping(value="${endpoint.login}")
	public ResponseEntity<JwtResponse> createAuthenticationToken( @RequestBody JwtRequest authenticationRequest) {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	/**
	 * Authenticate the credentials received
	 * @param username
	 * @param password
	 */
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new MineSweeperException(ErrorTypes.USER_DISABLED.toString());
		} catch (BadCredentialsException e) {
			throw new MineSweeperException(ErrorTypes.INVALID_CREDENTIALS.toString());
		}
	}
}

