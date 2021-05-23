package com.deviget.minesweeper.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import com.deviget.minesweeper.service.AccountService;

/**
 * Controller For Expose Account creation
 * @author Erneski Coronado
 *
 */
@RestController
@RequestMapping("${endpoint.root}${endpoint.v1}")
@Validated
public class AccountsController {

	@Autowired
	AccountService serv;
	
	/**
	 * Creates a new account
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping(value="${endpoint.accounts}")
	public ResponseEntity<String> createParty(@Valid @NotBlank  @Size(min=3, max=30) @RequestParam("username") String username,
			@Valid @NotBlank  @Size(min=3, max=30) @RequestParam("password") String password) {
		String user = HtmlUtils.htmlEscape(username);
		String pass = HtmlUtils.htmlEscape(password);
		
		
		if(serv.createAccount(user, pass)) {
			return ResponseEntity.ok("ACCOUNT CREATED FOR "+username);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AN ERROR HAPPENS CREATING ACCOUNT - TRY LATER");
	}
}
