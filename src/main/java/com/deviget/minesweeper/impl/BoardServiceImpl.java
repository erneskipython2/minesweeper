package com.deviget.minesweeper.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.BoardSettings;
import com.deviget.minesweeper.domain.ErrorTypes;
import com.deviget.minesweeper.domain.Field;
import com.deviget.minesweeper.exception.MineSweeperException;
import com.deviget.minesweeper.service.BoardService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for BoardService
 * 
 * @author Erneski Coronado
 *
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Override
	public Field[][] generateBoard(BoardSettings settings, boolean isPlayer) {

		int numberOfCells = settings.getRows() * settings.getColumns();

		if (numberOfCells < settings.getMines()) {
			throw new MineSweeperException(ErrorTypes.INVALID_MINES_NUMBER.toString());
		}

		log.debug("Creating a new board {}, NumberOfCells: {}", settings, numberOfCells);

		if(isPlayer) {
			return fillBoard(settings.getRows(), settings.getColumns());	
		}
		
		Set<Integer> minedFields = generateMines(numberOfCells - 1, settings.getMines());
		return fillBoard(settings.getRows(), settings.getColumns(), minedFields);		
	}

	/**
	 * Generates randomly the fields mined
	 * 
	 * @param numberOfCells
	 * @return
	 */
	private Set<Integer> generateMines(int numberOfCells, int numberOfMines) {
		Set<Integer> mines = new HashSet<>();
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");
			for (int i = 0; i < numberOfMines; i++) {
				boolean generated = false;
				while (!generated) {
					int possible = secureRandom.nextInt(numberOfCells);
					if (!mines.contains(possible)) {
						mines.add(possible);
						generated = true;
					}
				}
			}
		} catch (NoSuchAlgorithmException e) {
			log.error("Error generating the mines {}", e.getMessage());
		}
		return mines;
	}
	
	/**
	 * Fills the board with the Fields and mines
	 * @param rows
	 * @param columns
	 * @param minedFields
	 * @return A Board filled
	 */
	private Field[][] fillBoard(int rows, int columns, Set<Integer> minedFields) {
		Field [][] board = new Field[rows][columns];
		if(minedFields.isEmpty()) {
			throw new MineSweeperException(ErrorTypes.INVALID_MINES_NUMBER.toString());
		}
		
		int currentCell = 0;
		for (int r=0; r<rows; r++) {
			for(int c=0; c<columns; c++) {
				boolean isMined = minedFields.contains(currentCell);
				currentCell++;
				board[r][c] = new Field(isMined, false, false, 0);
			}
		}
		
		return board;
	}
	
	/**
	 * Fills the board with black Fields - This for client side
	 * @param rows
	 * @param columns
	 * @param minedFields
	 * @return A Board filled
	 */
	private Field[][] fillBoard(int rows, int columns) {
		Field [][] board = new Field[rows][columns];

		for (int r=0; r<rows; r++) {
			for(int c=0; c<columns; c++) {
				board[r][c] = new Field(false, false, false, 0);
			}
		}		
		return board;
	}
	
	

}
