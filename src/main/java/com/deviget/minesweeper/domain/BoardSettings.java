package com.deviget.minesweeper.domain;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.deviget.minesweeper.exception.MineSweeperException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Model for describe a game board with some default constructors
 * @author e0c05ua - Erneski Coronado
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BoardSettings {

	@Min(9)
	@Max(30)
	@NotNull
	private int rows;
	@Min(9)
	@Max(30)
	@NotNull
	private int columns;
	@Max(899)
	@Min(1)
	@NotNull
	private int mines;
	private String level;
	
	public static final String EASY = "EASY";
	public static final String NORMAL = "NORMAL";
	public static final String HARD = "HARD";
	public static final String CUSTOM = "CUSTOM";
	
	/**
	 * Convenience constructor for set BoardSetting with default values
	 * @param level
	 */
	public BoardSettings(String level) {
		this.level = level;
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
			case CUSTOM:
				this.rows = 9;
				this.columns = 9;
				this.mines = 10;
				break;
			default:
				throw new MineSweeperException(ErrorTypes.INVALID_LEVEL.toString());			
		}
	}
}
