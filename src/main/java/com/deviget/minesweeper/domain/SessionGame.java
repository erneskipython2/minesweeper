package com.deviget.minesweeper.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

/**
 * Model for describe the Session Game persistence in DB
 * @author Erneski Coronado
 *
 */
@Data
@Builder
@JsonInclude(Include.NON_NULL)
@Document(collection = "sessionGame")
public class SessionGame {

	@Id
	private String id;
	private String userId;
	private String state;
	private Date startGame;
	private Date lastUpdate;
	private Long timeTracking;
	
}
