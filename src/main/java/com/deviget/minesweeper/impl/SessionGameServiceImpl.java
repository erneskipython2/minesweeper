package com.deviget.minesweeper.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.ErrorTypes;
import com.deviget.minesweeper.domain.GameStates;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.exception.MineSweeperException;
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
		String newState = validateState(state);
		SessionGame persistence = validatePersistence(sessionRep.findById(id));
		
		Calendar start = Calendar.getInstance();
		Calendar lastUpdate = Calendar.getInstance();
		
		if(!persistence.getState().equals(GameStates.PAUSED.toString())) {
			start.setTime(persistence.getLastUpdate());
			long diff = lastUpdate.getTimeInMillis() - start.getTimeInMillis();
			long seconds = TimeUnit. MILLISECONDS. toSeconds(diff);
			persistence.setTimeTracking(persistence.getTimeTracking() + seconds );
		}
		persistence.setLastUpdate(lastUpdate.getTime());
		persistence.setState(newState);
		
		return sessionRep.save(persistence);

	}

	@Override
	public Boolean deleteParty(String id) {
		
		SessionGame persistence = validatePersistence(sessionRep.findById(id));
		sessionRep.delete(persistence);
		return true;
		
	}
	
	@Override
	public List<SessionGame> getSessionGames(String userId) {
		
		return sessionRep.findByUserId(userId);
	}

	@Override
	public SessionGame getSessionGame(String id) {
		
		return validatePersistence(sessionRep.findById(id));
	}
	
	
	/**
	 * Validate if the given state is valid
	 * @param state
	 * @return
	 */
	private String validateState(String state) {
		Boolean valid = false;
	    for (GameStates st : GameStates.values()) {
	        if (st.name().equalsIgnoreCase(state)) {
	        	valid =  true;
	        }
	            
	    }
	    if(Boolean.FALSE.equals(valid)) {
			throw new MineSweeperException(ErrorTypes.INVALID_STATE.toString());
		}
	    if(state.equalsIgnoreCase(GameStates.RESUME.toString())) {
	    	state = GameStates.PLAYING.toString();
	    }
	    return state.toUpperCase();
	    
	}
	
	/**
	 * Validate if a given Id for update exists and return the value if any
	 * @param persistence
	 */
	private SessionGame validatePersistence(Optional<SessionGame> persistence) {
		if(!persistence.isPresent()) {
			throw new MineSweeperException(ErrorTypes.INVALID_SESSION.toString());
		}
		return persistence.get();
	}


}
