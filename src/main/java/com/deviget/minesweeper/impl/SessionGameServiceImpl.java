package com.deviget.minesweeper.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.GameStates;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.repository.SessionGameRepositoryService;
import com.deviget.minesweeper.service.SessionGameService;

/**
 * Business logic Implementation for  SessionGameService
 * @author Erneski Coronado
 *
 */
@Service
public class SessionGameServiceImpl implements SessionGameService {

	@Autowired
	SessionGameRepositoryService sessionRep;
	
	@Override
	public SessionGame createParty(String userId) {
		
		Calendar cal = Calendar.getInstance();
		
		SessionGame session = SessionGame
				.builder()
				.userId(userId)
				.startGame(cal.getTime())
				.lastUpdate(cal.getTime())
				.timeTracking(0L)
				.state(GameStates.STARTED.toString())
				.build();
		return sessionRep.save(session);
	}

	@Override
	public SessionGame updateParty(String id, String state) {
		
		return new SessionGame("123", "123", "RESUME",  new Date(), new Date(), 60000L); 
	}

	@Override
	public Boolean deleteParty(String id) {
		
		return true;
	}

}
