package com.deviget.minesweeper.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deviget.minesweeper.domain.Field;
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
				
		if(playingBoard[row][column].isSafe()) {
			log.debug("Field already cleared - skip movement");
		}else if(flag) {
			playingBoard[row][column].setFlaged(true);
			log.debug("Field is flagged");
		}else if(game.getGeneratedBoard()[row][column].isMined()) {
			game.setState(GameStates.LOSE.toString());
			playingBoard[row][column].setMined(true);
			playingBoard[row][column].setSafe(false);
			playingBoard[row][column].setFlaged(false);
			log.debug("mined field, lose!");
		}else {
			playingBoard[row][column].setSafe(true);
			playingBoard[row][column].setAdyacentMines(game.getGeneratedBoard()[row][column].getAdyacentMines());
		}
						
		SessionGame updated = session.updateParty(id, game.getState(), Optional.of(playingBoard));
		String board = GameUtils.printBoard(updated, updated.getState().equals(GameStates.LOSE.toString()), updated.getState().equals(GameStates.LOSE.toString()));
		log.debug("Playing party session id {} -  board \n{}",game.getId(), board );
		return board;
	}

	@Override
	public String play(String id, boolean surrender) {
		SessionGame game = GameUtils.validatePersistence(sessionRep.findById(id));
		if(!surrender) {
			return GameUtils.printBoard(game, false, false);
		}
		game.setState(GameStates.LOSE.toString());
		SessionGame updated = session.updateParty(id, game.getState(), Optional.empty());		
		
		String board = GameUtils.printBoard(updated, true, true);
		log.debug("Surrender party session id {} -  board \n{}",game.getId(), board );
		return board;
	}
	
}
