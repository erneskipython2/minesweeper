package com.deviget.minesweeper.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for describe a Board Field
 * @author Erneski Coronado
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field {

	private boolean isMined;
	private boolean isFlaged;
	private boolean isSafe;
	private int adyacentMines;
	
	
	/**
	 * Represent visually the field state
	 */
	@Override
	public String toString() {
		
		if(this.isFlaged) {
			return "?";
		}
		else if(this.isSafe && adyacentMines > 0) {
			return Integer.toString(this.adyacentMines);
		}else if(this.isSafe && adyacentMines == 0) {
			return " ";
		}
		else {
			return "_";
		}
	}	
	
	public String revealBoard() {
		if(this.isFlaged) {
			return "?";
		}else if (this.isMined) {
			return "*";
		}else {
			return "_";
		}
	}
	
	
	
}
