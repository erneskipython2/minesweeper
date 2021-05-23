package com.deviget.minesweeper.config;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deviget.minesweeper.util.Constants;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * The JwtRequestFilter extends the Spring Web Filter OncePerRequestFilter class. 
 * For any incoming request this Filter class gets executed. 
 * It checks if the request has a valid JWT token. 
 * If it has a valid JWT Token then it sets the Authentication in the context, 
 * to specify that the current user is authenticated.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetails jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader(Constants.AUTHORIZATION_STR);

		String username = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith(Constants.BEARER_STR)) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);

			} catch (IllegalArgumentException e) {
				LOGGER.error(Constants.ERROR_MSG_GET_TOKEN);
			} catch (ExpiredJwtException e) {
				LOGGER.error(Constants.ERROR_MSG_EXP_TOKEN);
			}
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);	
			logger.info(userDetails.toString());

			if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails))) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		}
		chain.doFilter(request, response);
	}

}

