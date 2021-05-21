package com.deviget.minesweeper.service;

import com.deviget.minesweeper.domain.SessionGame;

/**
 * Basic definitions for Session Game Service interactions
 * @author Erneski Coronado
 *
 */
public interface SessionGameService {

	/**
	 * Creates a new party given the owner user
	 * @param userId
	 * @return The Created party
	 */
	SessionGame createParty(String userId);
	
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
