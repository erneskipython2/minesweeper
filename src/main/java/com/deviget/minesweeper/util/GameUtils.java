package com.deviget.minesweeper.util;

import java.util.Optional;

import com.deviget.minesweeper.domain.ErrorTypes;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.exception.MineSweeperException;

/**
 * Some helpers for the game
 * Erneski Coronado
 *
 */
public class GameUtils {
	
	private GameUtils() {
		//Default
	}

	/**
	 * Generates a string representation for a board
	 * @param rows
	 * @param columns
	 * @param board
	 * @param reveal
	 * @return
	 */
	public static final String printBoard(SessionGame game, boolean reveal) {
		
		String title =  "*****ⓂⒾⓃⒺⓈⓌⒺⒺⓅⒺⓇ*****";
		String author = "🅱🆈 🅴🆁🅽🅴🆂🅺🅸 🅲🅾🆁🅾🅽🅰🅳🅾";
		String instructions = "Instructions: Send with the row and column params your next move, Enjoy!";
		String boardHead = "[̲̅M][̲̅Y] [̲̅B][̲̅O][̲̅A][̲̅R][̲̅D]";
		
		StringBuilder bld = new StringBuilder();
		bld.append(title)
		.append(System.lineSeparator())
		.append(author)
		.append(System.lineSeparator())
		.append(System.lineSeparator())
		.append(instructions)
		.append(System.lineSeparator())
		.append("State: ")
		.append(game.getState())
		.append(" | Time Playing: ")
		.append(game.getTimeTracking())
		.append(" (seconds)")
		.append(System.lineSeparator())
		.append("Settings: ")
		.append(game.getSettings())
		.append(System.lineSeparator())
		.append(System.lineSeparator())
		.append(boardHead)
		.append(System.lineSeparator());
		bld.append(" |");
		for(int c=0; c<game.getSettings().getColumns(); c++) {
			bld.append(c).append("|");
		}
		bld.append(System.lineSeparator());
		
		for(int r=0; r<game.getSettings().getRows(); r++) {
			bld.append(r);
			bld.append("|");
			for(int c=0; c<game.getSettings().getColumns(); c++) {
				bld
				.append(reveal ? game.getPlayingBoard()[r][c].revealBoard() : game.getPlayingBoard()[r][c].toString())
				.append("|");
			}
			bld.append(System.lineSeparator());
		}
		return bld.toString();
	}
	
	/**
	 * Validate if a given Id for update exists and return the value if any
	 * @param persistence
	 */
	public static final SessionGame validatePersistence(Optional<SessionGame> persistence) {
		if(!persistence.isPresent()) {
			throw new MineSweeperException(ErrorTypes.INVALID_SESSION.toString());
		}
		return persistence.get();
	}
}