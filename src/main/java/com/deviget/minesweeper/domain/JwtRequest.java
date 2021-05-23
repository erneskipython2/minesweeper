package com.deviget.minesweeper.domain;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class JWT request authorization
 *
 */
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	

}
