package com.deviget.minesweeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.deviget.minesweeper.domain.SessionGame;
import com.deviget.minesweeper.service.SessionGameService;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller For Expose Session Game functionality
 * @author e0c05ua - Erneski Coronado
 *
 */
@RestController
@RequestMapping("${endpoint.root}${endpoint.v1}")
@Validated
@Slf4j
public class SessionGameController {

	@Autowired
	SessionGameService session;
	
	@PostMapping(value="${endpoint.session-games}")
	public ResponseEntity<SessionGame> createParty(@Valid @NotBlank  @Size(min=1, max=30) @RequestParam("userId") String userId) {
		String escapedUserId = HtmlUtils.htmlEscape(userId);
		return ResponseEntity.ok(session.createParty(escapedUserId));
	}
	
	@PutMapping(value="${endpoint.session-games}")
	public ResponseEntity<SessionGame> updateParty(@Valid @NotBlank  @Size(min=1, max=30) @RequestParam("id") String id,
			@Valid @NotBlank  @Size(min=6, max=10) @RequestParam("state") String state) {
		String escapedId = HtmlUtils.htmlEscape(id);
		String escapedState = HtmlUtils.htmlEscape(state);
		return ResponseEntity.ok(session.updateParty(escapedId, escapedState));
	}
	
}
