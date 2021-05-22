package com.deviget.minesweeper.service;

import java.util.List;

import com.deviget.minesweeper.domain.BoardSettings;
import com.deviget.minesweeper.domain.SessionGame;

/**
 * Basic definitions for Session Game Service interactions
 * @author Erneski Coronado
 *
 */
public interface SessionGameService {

	/**
	 * Return all the games initiated by the user
	 * @param userId
	 * @return
	 */
	List<SessionGame> getSessionGames(String userId);
	
	/**
	 * Returns a specific game
	 * @param id
	 * @return
	 */
	SessionGame getSessionGame(String id);
	
	/**
	 * Creates a new party given the owner user
	 * @param userId
	 * @return The Created party
	 */
	SessionGame createParty(String userId, BoardSettings settings);
	
	/**
	 * Updates a party
	 * @param id
	 * @param state
	 * @return
	 */
	SessionGame updateParty(String id, String state);
	
	/**
	 * Deletes a party
	 * @param id
	 * @return
	 */
	Boolean deleteParty(String id);
	
}
