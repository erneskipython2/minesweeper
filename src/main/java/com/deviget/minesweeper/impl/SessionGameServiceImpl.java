package com.deviget.minesweeper.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.service.SessionGameService;

/**
 * Business logic Implementation for  SessionGameService
 * @author Erneski Coronado
 *
 */
@Service
public class SessionGameServiceImpl implements SessionGameService {

	@Override
	public SessionGame createParty(String userId) {

		
		return new SessionGame("123", "123", "RESUME",  new Date(), new Date(), 60000L); 
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
