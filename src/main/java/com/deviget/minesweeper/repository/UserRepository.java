package com.deviget.minesweeper.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deviget.minesweeper.domain.UserDao;


public interface UserRepository extends MongoRepository<UserDao, String>{
	
	 Optional<UserDao> findByUsername(String username);

}
