package com.deviget.minesweeper.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.BoardSettings;
import com.deviget.minesweeper.domain.ErrorTypes;
import com.deviget.minesweeper.domain.Field;
import com.deviget.minesweeper.domain.GameStates;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.exception.MineSweeperException;
import com.deviget.minesweeper.repository.SessionGameRepositoryService;
import com.deviget.minesweeper.service.BoardService;
import com.deviget.minesweeper.service.SessionGameService;
import com.deviget.minesweeper.util.GameUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Business logic Implementation for  SessionGameService
 * @author Erneski Coronado
 *
 */
@Service
@Slf4j
public class SessionGameServiceImpl implements SessionGameService {

	@Autowired
	SessionGameRepositoryService sessionRep;
	@Autowired
	BoardService board;
	
	@Override
	public SessionGame createParty(String userId, BoardSettings settings) {
		
		Calendar cal = Calendar.getInstance();
		Field[][] generatedBoard = board.generateBoard(settings, false);
		Field[][] playingBoard = board.generateBoard(settings, true);
		SessionGame session = SessionGame
				.builder()
				.userId(userId)
				.startGame(cal.getTime())
				.lastUpdate(cal.getTime())
				.timeTracking(0L)
				.state(GameStates.STARTED.toString())
				.generatedBoard(generatedBoard)
				.playingBoard(playingBoard)
				.settings(settings)
				.build();
		log.info("Generated Board are \n{}",GameUtils.printBoard(session, true, true));
		return sessionRep.save(session);
	}

	@Override
	public SessionGame updateParty(String id, String state, Optional<Field [][]> board) {
		SessionGame persistence = GameUtils.validatePersistence(sessionRep.findById(id));
		String newState = validateState(state, persistence);
		
		
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
		if(board.isPresent()) {
			persistence.setPlayingBoard(board.get());
		}
		
		return sessionRep.save(persistence);

	}

	@Override
	public Boolean deleteParty(String id) {
		
		SessionGame persistence = GameUtils.validatePersistence(sessionRep.findById(id));
		sessionRep.delete(persistence);
		return true;
		
	}
	
	@Override
	public List<SessionGame> getSessionGames(String userId) {
		
		return sessionRep.findByUserId(userId);
	}

	@Override
	public SessionGame getSessionGame(String id) {
		
		return GameUtils.validatePersistence(sessionRep.findById(id));
	}
	
	
	/**
	 * Validate if the given state is valid
	 * @param state
	 * @return
	 */
	private String validateState(String state, SessionGame game) {
		boolean valid = false;
	    for (GameStates st : GameStates.values()) {
	        if (st.name().equalsIgnoreCase(state)) {
	        	valid =  true;
	        }
	            
	    }
	    if(!valid) {
			throw new MineSweeperException(ErrorTypes.INVALID_STATE.toString());
		}
	    
	    GameUtils.validateGameIsPlayable(game);
	    
	    if(state.equalsIgnoreCase(GameStates.RESUME.toString())) {
	    	state = GameStates.PLAYING.toString();
	    }
	    return state.toUpperCase();
	    
	}


}
