package com.deviget.minesweeper.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes a given coordinate in the board
 * @author Erneski Coronado
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldCoordinate {

	private int row;
	private int column;
}
