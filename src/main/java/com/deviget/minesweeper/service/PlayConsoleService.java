package com.deviget.minesweeper.service;

/**
 * Defines available operations for play by console or rest client 
 * @author Erneski Coronado
 *
 */
public interface PlayConsoleService {

	/**
	 * Return a party
	 * 
	 * @return The board in String format
	 */
	String play(String id);
	
	/**
	 * Sends a movement
	 * @param row
	 * @param column
	 * @return The board in String format result
	 */
	String play(String id, int row, int column);
	
	/**
	 * Surrender from a party
	 * @param surrender
	 * @return The board with all mines discovered
	 */
	String play(String id, Boolean surrender);
}
