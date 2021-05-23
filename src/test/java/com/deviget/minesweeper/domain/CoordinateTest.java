package com.deviget.minesweeper.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoordinateTest {

	@Test
	void enumTest() {
		assertEquals("EAST", Coordinates.EAST.toString());
		assertEquals("SOUTH", Coordinates.SOUTH.toString());
		assertEquals("EAST", Coordinates.EAST.toString());
		assertEquals("WEST", Coordinates.WEST.toString());
		assertEquals("NORTHEAST", Coordinates.NORTHEAST.toString());
		assertEquals("NORTHWEST", Coordinates.NORTHWEST.toString());
		assertEquals("SOUTHEAST", Coordinates.SOUTHEAST.toString());
		assertEquals("SOUTHWEST", Coordinates.SOUTHWEST.toString());
	}
}
