package com.deviget.minesweeper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.deviget.minesweeper.config.EndpointsConfig;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class MinesweeperApplicationTests {

	
	@Autowired
	EndpointsConfig config;
	
	@Test
	void contextLoads() {
		assertNotNull(config);
		MinesweeperApplication.main(new String[] {});
	}

}
