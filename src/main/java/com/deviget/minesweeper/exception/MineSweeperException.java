package com.deviget.minesweeper.exception;

/**
 * Basic Business Exception for validation failures and constraints violations
 * @author Erneski Coronado
 *
 */
public class MineSweeperException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5629272009696817204L;

	public MineSweeperException(String errorMessage) {
		super(errorMessage);
	}
}
