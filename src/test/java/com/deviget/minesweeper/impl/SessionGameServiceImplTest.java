package com.deviget.minesweeper.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.deviget.minesweeper.domain.GameStates;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.exception.MineSweeperException;
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
		SessionGame ses = service.createParty(MockedData.USER_ID, MockedData.BOARD_SETTINGS);
		assertNotNull(ses);
		
	}
	
	@Test
	void updatePartyTest() {
		Mockito.when(repo.save(Mockito.any(SessionGame.class))).thenReturn(session);
		Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(session));
		assertNotNull(service.updateParty(MockedData.ID, MockedData.STATE));
		
		session.setState(GameStates.PAUSED.toString());
		assertNotNull(service.updateParty(MockedData.ID, GameStates.RESUME.toString()));		
	}
	
	@Test
	void updatePartyFailureValidation() {
		assertThatThrownBy(() -> service.updateParty(MockedData.ID, "ANY_INVALID_STATE"))
        .isInstanceOf(MineSweeperException.class);
	}
	
	@Test
	void updatePartyFailurePersistence() {
		Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.updateParty(MockedData.ID, MockedData.STATE))
        .isInstanceOf(MineSweeperException.class);
	}
	
	@Test
	void deletePartyTest() {
		Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(session));
		Mockito.doNothing().when(repo).deleteById(Mockito.anyString());
		assertTrue(service.deleteParty(MockedData.ID));
	}
	
	@Test
	void findParty() {
		Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(session));
		Mockito.when(repo.findByUserId(Mockito.anyString())).thenReturn(Arrays.asList(session));
		assertNotNull(service.getSessionGames(MockedData.USER_ID));
		assertNotNull(service.getSessionGame(MockedData.ID));
	}
}
