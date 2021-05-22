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
	
	
	/**
	 * Represent visually the field state
	 */
	@Override
	public String toString() {
		if(this.isFlaged) {
			return "?";
		}else {
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
