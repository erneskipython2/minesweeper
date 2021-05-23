package com.deviget.minesweeper.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.deviget.minesweeper.domain.BoardSettings;
import com.deviget.minesweeper.domain.Coordinates;
import com.deviget.minesweeper.domain.ErrorTypes;
import com.deviget.minesweeper.domain.Field;
import com.deviget.minesweeper.domain.FieldCoordinate;
import com.deviget.minesweeper.domain.GameStates;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.exception.MineSweeperException;

import lombok.extern.slf4j.Slf4j;

/**
 * Some helpers for the game
 * Erneski Coronado
 *
 */
@Slf4j
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
	public static final String printBoard(SessionGame game, boolean reveal, boolean realBoard) {
		
		String title =  "*****ⓂⒾⓃⒺⓈⓌⒺⒺⓅⒺⓇ*****";
		String author = "🅱🆈 🅴🆁🅽🅴🆂🅺🅸 🅲🅾🆁🅾🅽🅰🅳🅾";
		String instructions = "Instructions: Send with the row and column params your next move, Enjoy!";
		String boardHead = "[̲̅M][̲̅Y] [̲̅B][̲̅O][̲̅A][̲̅R][̲̅D]";
		String legend = "☐ -> unknow | ☑ -> safe | [1..n] -> Number of Mines adjacent | � -> Flagged | ☠ -> Mined, so you losed :(";
		
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
		.append(" | Movements: ")
		.append(game.getMovements())
		.append(System.lineSeparator())
		.append("Settings: ")
		.append(game.getSettings())
		.append(System.lineSeparator())
		.append("Legend: ")
		.append(legend)
		.append(System.lineSeparator())
		.append(System.lineSeparator())
		.append(boardHead)
		.append(System.lineSeparator());
		bld.append("   |");
		for(int c=0; c<game.getSettings().getColumns(); c++) {
			bld.append(c).append("|");
		}
		bld.append(System.lineSeparator());
		
		for(int r=0; r<game.getSettings().getRows(); r++) {
			if(r<=9) {
				bld.append(" ");
			}
			bld.append(r).append(" ");
			bld.append("|");
			for(int c=0; c<game.getSettings().getColumns(); c++) {
				Field[][] generatedBoard;
				if(realBoard) {
					generatedBoard = game.getGeneratedBoard();
				}else {
					generatedBoard = game.getPlayingBoard();
				}
				if(c>9) {
					bld.append(" ");
				}
				bld
				.append(reveal ? generatedBoard[r][c].revealBoard() : generatedBoard[r][c].toString())
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
	
	/**
	 * Validates if a given coordinates exists for a board configuration
	 * @param row
	 * @param column
	 * @param settings
	 * @return
	 */
	public static final boolean validateFieldExists(int row, int column, BoardSettings settings) {
		return (
				(row >= 0 && row <settings.getRows()) 
				&& 
				(column >=0 && column <settings.getColumns())
				);
	}
	
	/**
	 * Validates is the game is playable yet
	 * @param game
	 */
	public static final void validateGameIsPlayable(SessionGame game) {
		List<String> endedStates = new ArrayList<>();
		endedStates.add(GameStates.LOSE.toString());
		endedStates.add(GameStates.RESIGNED.toString());
		endedStates.add(GameStates.WON.toString());
		boolean playable = true;
		for(String end: endedStates) {
			if(end.equals(game.getState())) {
				playable = false;
				break;
			}
		}
		if(!playable) {
			throw new MineSweeperException(ErrorTypes.GAME_ENDED.toString());
		}

	}
	
	/**
	 * Generates a map of field adjacent coordinates for a given combination of row a column
	 * @param row
	 * @param column
	 * @return
	 */
	public static final Map<Coordinates, FieldCoordinate> getAdjacentMap(int row, int column, SessionGame game){
		 Map<Coordinates, FieldCoordinate> adjacents = new LinkedHashMap<>();
		 Field[][] generatedBoard = game.getGeneratedBoard();
		 adjacents.put(Coordinates.NORTH, FieldCoordinate.builder().row(row-1).column(column).build());
		 adjacents.put(Coordinates.SOUTH, FieldCoordinate.builder().row(row+1).column(column).build());
		 adjacents.put(Coordinates.EAST, FieldCoordinate.builder().row(row).column(column+1).build());
		 adjacents.put(Coordinates.WEST, FieldCoordinate.builder().row(row).column(column-1).build());
		 adjacents.put(Coordinates.NORTHEAST, FieldCoordinate.builder().row(row-1).column(column+1).build());
		 adjacents.put(Coordinates.NORTHWEST, FieldCoordinate.builder().row(row-1).column(column-1).build());
		 adjacents.put(Coordinates.SOUTHEAST, FieldCoordinate.builder().row(row+1).column(column+1).build());
		 adjacents.put(Coordinates.SOUTHWEST, FieldCoordinate.builder().row(row+1).column(column-1).build());
		 Map<Coordinates, FieldCoordinate> adjacentsCleaned = new LinkedHashMap<>();
		 for (Map.Entry<Coordinates,FieldCoordinate> entry : adjacents.entrySet()) {
			 FieldCoordinate coordinate = entry.getValue();
			 if(validateFieldExists(coordinate.getRow(), coordinate.getColumn(), game.getSettings())
					 &&
					 (!generatedBoard[coordinate.getRow()][coordinate.getColumn()].isMined())) {
				 adjacentsCleaned.put(entry.getKey(), entry.getValue());
			 }
			 
		 }
		 return adjacentsCleaned;
	}
	
	/**
	 * Init the BoardSettings with default or custom options
	 * @param level
	 * @param row
	 * @param columns
	 * @param mines
	 * @return
	 */
	public static final BoardSettings initSettings(Optional<String> level, 
			Optional<Integer> row,
			Optional<Integer> columns,
			Optional<Integer> mines) {
		BoardSettings settings = new BoardSettings(level.orElse(BoardSettings.EASY));
		if(row.isPresent() || columns.isPresent() || mines.isPresent()) {
			settings.setLevel(BoardSettings.CUSTOM);
		}
		if(row.isPresent()) {
			settings.setRows(row.get());
		}
		if(columns.isPresent()) {
			settings.setColumns(columns.get());
		}
		if(mines.isPresent()) {
			settings.setMines(mines.get());
		}
		return settings;
	}
	
	/**
	 * Reorganize the board moving a mine from the given position
	 * @param game
	 * @param row
	 * @param column
	 */
	public static final FieldCoordinate reorderGeneratedBoar(SessionGame game, int row, int column) {
		FieldCoordinate coord = new FieldCoordinate(row, column);
		try {
			Field[][] generatedBoard = game.getGeneratedBoard().clone();
			SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");
			boolean inmigrate = true;
			while(inmigrate) {
				int newRow = secureRandom.nextInt(game.getSettings().getRows());
				int newColumn = secureRandom.nextInt(game.getSettings().getColumns());
				if((newRow == row && newColumn == column) || 
						generatedBoard[newRow][newColumn].isMined()) {
					continue;
				}
				//generatedBoard[row][column].setMined(false);
				//generatedBoard[newRow][newColumn].setMined(true);
				log.info("mine moved to row: {} column: {}", newRow, newColumn);
				coord.setRow(newRow);
				coord.setColumn(newColumn);
				inmigrate = false;
			}
			//game.setGeneratedBoard(generatedBoard);
			
		}catch(NoSuchAlgorithmException e) {
			log.error("Error reordering the board {}", e.getMessage());
		}
		return coord;
	}
	
}
