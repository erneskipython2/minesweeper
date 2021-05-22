package com.deviget.minesweeper.impl;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.Coordinates;
import com.deviget.minesweeper.domain.Field;
import com.deviget.minesweeper.domain.FieldCoordinate;
import com.deviget.minesweeper.domain.GameStates;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.repository.SessionGameRepositoryService;
import com.deviget.minesweeper.service.PlayConsoleService;
import com.deviget.minesweeper.service.SessionGameService;
import com.deviget.minesweeper.util.GameUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for PlayConsoleService
 * @author Erneski Coronado
 *
 */
@Service
@Slf4j
public class PlayConsoleServiceImpl implements PlayConsoleService {

	@Autowired
	SessionGameRepositoryService sessionRep;
	@Autowired
	SessionGameService session;

	@Override
	public String play(String id) {
		SessionGame game = GameUtils.validatePersistence(sessionRep.findById(id));
		String board = GameUtils.printBoard(game, false, false);
		log.debug("Retrieve party session id {} -  board \n{}",game.getId(), board );
		return board;
	}

	@Override
	public String play(String id, int row, int column, boolean flag) {
		SessionGame game = GameUtils.validatePersistence(sessionRep.findById(id));
		Field[][] playingBoard = game.getPlayingBoard();
		boolean updateGeneratedBoard = false;	
		if(game.getMovements() == 0) {
			GameUtils.reorderGeneratedBoar(game, row, column);
			updateGeneratedBoard = true;
		}
		
		if(playingBoard[row][column].isSafe()) {
			log.debug("Field already cleared - skip movement");
		}else if(flag) {
			playingBoard[row][column].setFlaged(true);
			game.setPlayingBoard(playingBoard);
			log.debug("Field is flagged");
			game.setMovements(game.getMovements() +1);
		}else if(game.getGeneratedBoard()[row][column].isMined()) {
			game.setState(GameStates.LOSE.toString());
			playingBoard[row][column].setMined(true);
			playingBoard[row][column].setSafe(false);
			playingBoard[row][column].setFlaged(false);
			game.setPlayingBoard(playingBoard);
			game.setMovements(game.getMovements() +1);
			log.debug("mined field, lose!");
		}else {			
			playingBoard[row][column].setSafe(true);
			playingBoard[row][column].setAdyacentMines(game.getGeneratedBoard()[row][column].getAdyacentMines());
			game.setPlayingBoard(playingBoard);
			game.setMovements(game.getMovements() +1);
			if(playingBoard[row][column].getAdyacentMines()==0) {
				Map<Coordinates, FieldCoordinate> adjacents = GameUtils.getAdjacentMap(row, column, game);
				try {
					cleanRecursive(adjacents, game);
				}catch(Exception e) {
					log.error("Unexpected behavior in recursive clean {}",e.getMessage());
				}
				
			}
		}
						
		SessionGame updated = session.updateParty(id, game.getState(), game.getMovements(), Optional.of(game.getPlayingBoard()), updateGeneratedBoard? Optional.of(game.getGeneratedBoard()) : Optional.empty());
		String board = GameUtils.printBoard(updated, updated.getState().equals(GameStates.LOSE.toString()), updated.getState().equals(GameStates.LOSE.toString()));
		log.debug("Playing party session id {} -  board \n{}",game.getId(), board );
		return board;
	}
	
	/**
	 * Clean recursively the board if possible
	 * @param adjacents
	 * @param game
	 */
	private void cleanRecursive(Map<Coordinates, FieldCoordinate> adjacents, SessionGame game ){
		for (Map.Entry<Coordinates,FieldCoordinate> entry : adjacents.entrySet()) {
			FieldCoordinate current = entry.getValue();
			int row = current.getRow();
			int column = current.getColumn();				
			Field[][] playingBoard = game.getPlayingBoard();
			Field[][] generatedBoard = game.getGeneratedBoard();
			
			if(playingBoard[row][column].isSafe()) {
				continue;
			}
			playingBoard[row][column].setSafe(true);
			playingBoard[row][column].setAdyacentMines(generatedBoard[row][column].getAdyacentMines());
			game.setPlayingBoard(playingBoard);
			if(playingBoard[row][column].getAdyacentMines()==0) {
				Map<Coordinates, FieldCoordinate> relativeAdjacents = GameUtils.getAdjacentMap(row, column, game);
				cleanRecursive(relativeAdjacents, game);
			}
			
		}
	}
	

	@Override
	public String play(String id, boolean surrender) {
		SessionGame game = GameUtils.validatePersistence(sessionRep.findById(id));
		if(!surrender) {
			return GameUtils.printBoard(game, false, false);
		}
		game.setState(GameStates.RESIGNED.toString());
		SessionGame updated = session.updateParty(id, game.getState(), 0, Optional.empty(), Optional.empty());		
		
		String board = GameUtils.printBoard(updated, true, true);
		log.debug("Surrender party session id {} -  board \n{}",game.getId(), board );
		return board;
	}
	
}
