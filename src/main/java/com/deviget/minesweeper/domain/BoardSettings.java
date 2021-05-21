package com.deviget.minesweeper.domain;


import com.deviget.minesweeper.exception.MineSweeperException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * Model for describe a game board with some default constructors
 * @author e0c05ua - Erneski Coronado
 *
 */
@Builder
@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BoardSettings {

	private int rows;
	private int columns;
	private int mines;
	
	public static final String EASY = "EASY";
	public static final String NORMAL = "NORMAL";
	public static final String HARD = "HARD";
	
	/**
	 * Convenience constructor for set BoardSetting with default values
	 * @param level
	 */
	public BoardSettings(String level) {
		switch(level.toUpperCase()) {
			case EASY: 
				this.rows = 9;
				this.columns = 9;
				this.mines = 10;
				break;
			case NORMAL:
				this.rows = 16;
				this.columns = 16;
				this.mines = 40;
				break;
			case HARD:
				this.rows = 24;
				this.columns = 24;
				this.mines = 99;
				break;
			default:
				throw new MineSweeperException(ErrorTypes.INVALID_LEVEL.toString());			
		}
	}
}
