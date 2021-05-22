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
	 * @param settings Settings for build the board
	 * @param isPlayer Indicates if the board is for client side (should not add the mines)
	 * @return
	 */
	Field[][] generateBoard(BoardSettings settings, boolean isPlayer);
}
