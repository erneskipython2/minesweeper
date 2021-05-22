package com.deviget.minesweeper.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deviget.minesweeper.exception.MineSweeperException;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
class BoardSettingsTest {

	@Test
	void customTest() {
        
        BoardSettings custom =  BoardSettings.builder().columns(9).rows(9).mines(10).build();
        BoardSettings easy = new BoardSettings(BoardSettings.EASY);
        BoardSettings normal = new BoardSettings(BoardSettings.NORMAL);
        BoardSettings hard = new BoardSettings(BoardSettings.HARD);
        BoardSettings customFull = new BoardSettings(9,9,10, BoardSettings.CUSTOM);
        
        assertEquals(custom, easy); 
        assertEquals(custom, customFull); 
        assertTrue((easy.getColumns() < normal.getColumns()) && (normal.getColumns() < hard.getColumns()));
        EqualsVerifier.simple().forClass(BoardSettings.class).verify();
        BoardSettings.builder().toString();
        customFull.setColumns(18);
        customFull.setRows(18);
        customFull.setMines(30);
        assertTrue(customFull.toString().contains("rows=18"));
        assertEquals(18, customFull.getColumns());
        assertEquals(18, customFull.getRows());
        assertEquals(30, customFull.getMines());
	}
	
	@Test
	void invalidLevelTest() {
		assertThatThrownBy(() -> new BoardSettings("HYPERHARD"))
        .isInstanceOf(MineSweeperException.class);
	}
	
}
