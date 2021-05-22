package com.deviget.minesweeper.service;

import com.deviget.minesweeper.domain.BoardSettings;
import com.deviget.minesweeper.domain.Field;

/**
 * Describes the available operations for Board Game
 * @author Erneski Coronado
 *
 */
public interface BoardService {

	/**
	 * Generates a board
	 * @param settings
	 * @return the board
	 */
	Field[][] generateBoard(BoardSettings settings);
}
