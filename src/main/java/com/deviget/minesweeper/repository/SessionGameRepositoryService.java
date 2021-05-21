package com.deviget.minesweeper.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.deviget.minesweeper.domain.SessionGame;

/**
 * Basic definitions for database persistence for SessionGame
 * @author Erneski Coronado
 *
 */
public interface SessionGameRepositoryService extends MongoRepository<SessionGame, String> {
	
	@Query(value="{ 'userId' : ?0 }", fields="{'state': 1, 'timeTracking': 1}")
	List<SessionGame> findByUserId(String userId);
}
