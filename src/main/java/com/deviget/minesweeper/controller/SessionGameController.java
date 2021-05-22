package com.deviget.minesweeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.deviget.minesweeper.domain.BoardSettings;
import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.service.PlayConsoleService;
import com.deviget.minesweeper.service.SessionGameService;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Controller For Expose Session Game functionality
 * @author e0c05ua - Erneski Coronado
 *
 */
@RestController
@RequestMapping("${endpoint.root}${endpoint.v1}")
@Validated
public class SessionGameController {

	@Autowired
	SessionGameService session;
	@Autowired
	PlayConsoleService play;
	
	/**
	 * Creates a party
	 * @param userId
	 * @param level
	 * @param row
	 * @param columns
	 * @return
	 */
	@PostMapping(value="${endpoint.session-games}")
	public ResponseEntity<SessionGame> createParty(@Valid @NotBlank  @Size(min=1, max=30) @RequestParam("userId") String userId,
			Optional<String> level, 
			Optional<Integer> row,
			Optional<Integer> columns) {
		String escapedUserId = HtmlUtils.htmlEscape(userId);
		String gameLevel = level.isPresent() ? level.get().toUpperCase() :  BoardSettings.EASY;
		BoardSettings settings = new BoardSettings(gameLevel);
		if(row.isPresent()) {
			settings.setRows(row.get());
		}
		if(columns.isPresent()) {
			settings.setColumns(columns.get());
		}
		SessionGame game = session.createParty(escapedUserId, settings);
		SessionGame response = SessionGame.builder()
				.id(game.getId())
				.userId(game.getUserId())
				.state(game.getState())
				.startGame(game.getStartGame())
				.build();
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Updates the session state for a party
	 * @param id
	 * @param state
	 * @return
	 */
	@PatchMapping(value="${endpoint.session-games}")
	public ResponseEntity<SessionGame> updateParty(@Valid @NotBlank  @Size(min=1, max=30) @RequestParam("id") String id,
			@Valid @NotBlank  @Size(min=6, max=10) @RequestParam("state") String state) {
		String escapedId = HtmlUtils.htmlEscape(id);
		String escapedState = HtmlUtils.htmlEscape(state);
		SessionGame game = session.updateParty(escapedId, escapedState, Optional.empty());
		SessionGame response = SessionGame.builder()
				.id(game.getId())
				.userId(game.getUserId())
				.state(game.getState())
				.startGame(game.getStartGame())
				.lastUpdate(game.getLastUpdate())
				.timeTracking(game.getTimeTracking())
				.build();
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Deletes a party
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="${endpoint.session-games}")
	public ResponseEntity<Void> deleteParty(@Valid @NotBlank  @Size(min=1, max=30) @RequestParam("id") String id) {
		String escapedId = HtmlUtils.htmlEscape(id);
		if(Boolean.TRUE.equals(session.deleteParty(escapedId))) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	/**
	 * Returns the list of parties for a user
	 * @param userId
	 * @return
	 */
	@GetMapping(value= {"${endpoint.session-games}/{userId}"})
	public ResponseEntity<List<SessionGame>> getSessionParties(@PathVariable @NotBlank  @Size(min=1, max=30) String userId) {

		return ResponseEntity.ok(session.getSessionGames(HtmlUtils.htmlEscape(userId)));
		
	}
	
	/**
	 * Returns a session party details
	 * @param userId
	 * @param id
	 * @return
	 */
	@GetMapping(value= {"${endpoint.session-games}/{userId}/{id}"})
	public ResponseEntity<SessionGame> getSessionParties(@PathVariable @NotBlank  @Size(min=1, max=30) String userId, 
			@PathVariable @NotBlank  @Size(min=1, max=30) String id){
		
		return ResponseEntity.ok(session.getSessionGame(HtmlUtils.htmlEscape(id)));
		
	}

	/**
	 * A convenience endpoint for get the board console-like view without affecting the party
	 * @param userId
	 * @param id
	 * @return
	 */
	@GetMapping(value= {"${endpoint.session-games}/{userId}/{id}${endpoint.play}"})
	public ResponseEntity<String> playParty(@PathVariable @NotBlank  @Size(min=1, max=30) String userId, 
			@PathVariable @NotBlank  @Size(min=1, max=30) String id){
			
		return ResponseEntity.ok(play.play(HtmlUtils.htmlEscape(id)));
	}
	
	@PatchMapping(value= {"${endpoint.session-games}/{userId}/{id}${endpoint.play}"})
	public ResponseEntity<String> playParty(@PathVariable @NotBlank  @Size(min=1, max=30) String userId, 
			@PathVariable @NotBlank  @Size(min=1, max=30) String id,
			@RequestParam("row") Optional<Integer> row,
			@RequestParam("column") Optional<Integer> column,
			@RequestParam("flag") Optional<Boolean> flag,
			@RequestParam("surrender") Optional<Boolean> surrender){
			
		//limpiar en business este update
		if(surrender.isPresent()) {
			return ResponseEntity.ok(play.play(HtmlUtils.htmlEscape(id), surrender.get()));
		}
		
		if(row.isPresent() && column.isPresent()) {
			return ResponseEntity.ok(play.play(HtmlUtils.htmlEscape(id), row.get(), column.get(), surrender.orElse(false)));
		}
		return playParty(userId, id);
	}
	
}
