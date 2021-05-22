package com.deviget.minesweeper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.repository.SessionGameRepositoryService;
import com.deviget.minesweeper.service.PlayConsoleService;
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

	@Override
	public String play(String id) {
		SessionGame game = GameUtils.validatePersistence(sessionRep.findById(id));
		String board = GameUtils.printBoard(game, false);
		log.debug("Retrieve party session id {} -  board \n{}",game.getId(), board );
		return board;
	}

	@Override
	public String play(String id, int row, int column) {
		return null;
	}

	@Override
	public String play(String id, Boolean surrender) {
		SessionGame game = GameUtils.validatePersistence(sessionRep.findById(id));
		String board = GameUtils.printBoard(game, true);
		log.debug("Surrender party session id {} -  board \n{}",game.getId(), board );
		return board;
	}
	


}
