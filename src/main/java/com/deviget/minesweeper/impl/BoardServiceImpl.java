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
import com.deviget.minesweeper.util.GameUtils;

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

		if (numberOfCells -1 < settings.getMines() || settings.getMines() <1) {
			throw new MineSweeperException(ErrorTypes.INVALID_MINES_NUMBER.toString());
		}

		log.debug("Creating a new board {}, NumberOfCells: {}", settings, numberOfCells);

		if(isPlayer) {
			return fillBoard(settings.getRows(), settings.getColumns());	
		}
		
		Set<Integer> minedFields = generateMines(numberOfCells - 1, settings.getMines());
		Field[][] filledBoard = fillBoard(settings.getRows(), settings.getColumns(), minedFields);
		countMines(settings, filledBoard);
		return filledBoard;		
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
	
	/**
	 * Sets the number of mines adyacent for each field
	 * @param settings
	 * @param board
	 */
	public void countMines(BoardSettings settings, Field[][] board) {

		for(int r=0; r<settings.getRows(); r++) {
			for(int c=0; c<settings.getColumns(); c++) {
				int north = setCount(r-1, c, settings, board);
				int south = setCount(r+1, c, settings, board);
				int east = setCount(r, c+1, settings, board);
				int west = setCount(r, c-1, settings, board);
				int northEast = setCount(r-1, c+1, settings, board);
				int northWest = setCount(r-1, c-1, settings, board);
				int southEast = setCount(r+1, c+1, settings, board);
				int southWest = setCount(r+1, c-1, settings, board);
				int total = north + south + east + west + northEast + northWest  + southEast + southWest;
				log.debug("Mines for Field {}{} "
						+ "north: {} "
						+ "south: {} "
						+ "east: {} "
						+ "west: {} "
						+ "northEast: {} "
						+ "northWest: {} "
						+ "southEast: {} "
						+ "southWest: {} "
						+ "total: {}", r,c,north,south,east,west,northEast,northWest,southEast,southWest,total);
				board[r][c].setAdyacentMines(total);								
			}
		}
	}
	
	/**
	 * Evaluates is there are a mine at a given coordinate for a field
	 * @param row
	 * @param column
	 * @param settings
	 * @param board
	 * @return the count
	 */
	private int setCount(int row, int column, BoardSettings settings, Field[][] board) {
		if(!GameUtils.validateFieldExists(row, column, settings)) {
			return 0;
		}
		if(board[row][column].isMined()) {
			return 1;
		}else {
			return 0;
		}
		
	}	

}
