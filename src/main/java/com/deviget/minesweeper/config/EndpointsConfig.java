package com.deviget.minesweeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for Endpoint configuration
 * @author Erneski Coronado
 */
@Configuration
@ConfigurationProperties(prefix = "endpoint")
@Getter 
@Setter
@NoArgsConstructor
@ToString
public class EndpointsConfig {

	private String root;
	private String v1;
	private String sessionGames;
	private String play;
}
