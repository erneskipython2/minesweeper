package com.deviget.minesweeper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.deviget.minesweeper.domain.BoardSettings;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.service.SessionGameService;
import com.deviget.minesweeper.utils.MockedData;

@SpringBootTest
class SessionGameControllerTest {

	@Mock
	SessionGameService service;
	
	@InjectMocks
	SessionGameController ctrl;
	
	private Date start = MockedData.generateGameDate(2021, 4, 20, 2, 9, 0);
    private Date end = MockedData.generateGameDate(2021, 4, 20, 2, 10, 0);
    private Long timeTracking = MockedData.generateTimeTracking(start, end);
	
	
	private SessionGame session = SessionGame
    		.builder()
    		.id(MockedData.ID)
    		.userId(MockedData.USER_ID)
    		.state(MockedData.STATE)
    		.startGame(start)
    		.lastUpdate(end)
    		.timeTracking(timeTracking)
    		.build();
	
	@Test
	void postTest() {
		Mockito.when(service.createParty(Mockito.anyString(), Mockito.any(BoardSettings.class))).thenReturn(session);
		
		assertEquals(HttpStatus.OK, ctrl.createParty("123", 
				Optional.of(BoardSettings.EASY),
				Optional.of(9),
				Optional.of(9)).getStatusCode());
	}
	
	@Test
	void putTest() {
		Mockito.when(service.updateParty(Mockito.anyString(), Mockito.anyString())).thenReturn(session);
		
		assertEquals(HttpStatus.OK, ctrl.updateParty("123", "abcd").getStatusCode());
	}
	
	@Test
	void delTest() {
		Mockito.when(service.deleteParty(Mockito.anyString())).thenReturn(true);
		
		assertEquals(HttpStatus.OK, ctrl.deleteParty("123").getStatusCode());
		
		Mockito.when(service.deleteParty(Mockito.anyString())).thenReturn(false);
		
		assertEquals(HttpStatus.NOT_FOUND, ctrl.deleteParty("123").getStatusCode());
	}
	
	@Test
	void getTest() {
		Mockito.when(service.getSessionGames(Mockito.anyString())).thenReturn(Arrays.asList(session));
		
		assertEquals(HttpStatus.OK, ctrl.getSessionParties("123").getStatusCode());
		
		Mockito.when(service.getSessionGame(Mockito.anyString())).thenReturn(session);
		
		assertEquals(HttpStatus.OK, ctrl.getSessionParties("123", "abcd").getStatusCode());
	}
	
}
