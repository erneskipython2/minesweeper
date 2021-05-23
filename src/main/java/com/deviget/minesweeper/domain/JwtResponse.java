package com.deviget.minesweeper.domain;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for the JWT authorization response
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private String jwttoken;
	
	public JwtResponse(String token) {
		this.jwttoken = token;
	}


}
