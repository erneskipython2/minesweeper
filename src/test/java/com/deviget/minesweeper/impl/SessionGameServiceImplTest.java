package com.deviget.minesweeper.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.repository.SessionGameRepositoryService;
import com.deviget.minesweeper.service.SessionGameService;
import com.deviget.minesweeper.utils.MockedData;

@SpringBootTest
class SessionGameServiceImplTest {
	
	@Mock
	SessionGameRepositoryService repo;
	
	@InjectMocks
	SessionGameService service = new SessionGameServiceImpl();
	
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
	void createPartyTest() {
		Mockito.when(repo.save(Mockito.any(SessionGame.class))).thenReturn(session);
		SessionGame ses = service.createParty(MockedData.USER_ID);
		assertNotNull(ses);
		
	}
	
	@Test
	void updatePartyTest() {
		Mockito.when(repo.save(Mockito.any(SessionGame.class))).thenReturn(session);
		SessionGame ses = service.updateParty(MockedData.ID, MockedData.STATE);
		assertNotNull(ses);
	}
	
	@Test
	void deletePartyTest() {
		Mockito.doNothing().when(repo).deleteById(Mockito.anyString());
		assertTrue(service.deleteParty(MockedData.ID));
	}
}
