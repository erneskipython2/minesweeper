package com.deviget.minesweeper.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

/**
 * Web Security for Rest Endpoints
 * @author Erneski Coronado
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConfigurationProperties
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Value("#{'${cors.allowed}'.split(',')}")
	private List<String> allowedEndpoints;
	
	@Autowired
	private EndpointsConfig props;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable()
        .authorizeRequests()//NOSONAR
				.antMatchers(HttpMethod.GET, "/actuator/logfile").authenticated()
				  .antMatchers(HttpMethod.GET, "/actuator/info").authenticated()
				  .antMatchers(HttpMethod.GET, "/actuator/health").authenticated()
				  .antMatchers(HttpMethod.POST, props.getRoot() + props.getV1() + props.getAccounts()).permitAll()
				  .antMatchers(HttpMethod.POST, props.getRoot() + props.getV1() + props.getLogin()).permitAll()
		          .antMatchers(HttpMethod.POST, props.getRoot() + props.getV1() + props.getSessionGames()).authenticated()
		          .antMatchers(HttpMethod.PATCH, props.getRoot() + props.getV1() + props.getSessionGames()).authenticated()
		          .antMatchers(HttpMethod.GET, props.getRoot() + props.getV1() + props.getSessionGames() + "/*").authenticated()
		          .antMatchers(HttpMethod.GET, props.getRoot() + props.getV1() + props.getSessionGames() + "/*/*").authenticated()
		          .antMatchers(HttpMethod.DELETE, props.getRoot() + props.getV1() + props.getSessionGames() + "/*/*").authenticated()
		          .antMatchers(HttpMethod.PATCH, 
		        		  props.getRoot() + props.getV1() + props.getSessionGames() + "/*/*" + props.getPlay() ).authenticated()
				.anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedEndpoints);
        configuration.setAllowedMethods(ImmutableList.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
		
}

