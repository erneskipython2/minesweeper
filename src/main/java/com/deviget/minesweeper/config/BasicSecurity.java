package com.deviget.minesweeper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * 
 * Basic Http Security
 * @author Erneski Coronado
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	EndpointsConfig props; 

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .cors().and().csrf().disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.GET, "/actuator/logfile").authenticated()
		  .antMatchers(HttpMethod.GET, "/actuator/info").authenticated()
		  .antMatchers(HttpMethod.GET, "/actuator/health").authenticated()
          .antMatchers(HttpMethod.POST, props.getRoot() + props.getV1() + props.getSessionGames()).authenticated()
          .antMatchers(HttpMethod.PATCH, props.getRoot() + props.getV1() + props.getSessionGames()).authenticated()
          .antMatchers(HttpMethod.GET, props.getRoot() + props.getV1() + props.getSessionGames() + "/*").authenticated()
          .antMatchers(HttpMethod.GET, props.getRoot() + props.getV1() + props.getSessionGames() + "/*/*").authenticated()
          .antMatchers(HttpMethod.DELETE, props.getRoot() + props.getV1() + props.getSessionGames() + "/*/*").authenticated()
          .antMatchers(HttpMethod.PATCH, 
        		  props.getRoot() + props.getV1() + props.getSessionGames() + "/*/*" + props.getPlay() ).authenticated()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic();
    }
}
