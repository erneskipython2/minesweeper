package com.deviget.minesweeper.utils;

import java.util.Calendar;
import java.util.Date;

import com.deviget.minesweeper.domain.BoardSettings;

/**
 * Mocked data for testing
 * @author Erneski Coronado
 *
 */
public class MockedData {

	private MockedData() {
		// default
	}

	public static final String ID = "123";
	public static final String USER_ID = "1234";
	public static final String STATE = "PLAYING";
	public static final BoardSettings BOARD_SETTINGS =  BoardSettings.builder()
			.columns(9)
			.rows(9)
			.mines(10)
			.level(BoardSettings.EASY)
			.build();

	/**
	 * Only for testing clean code
	 * Generates a static Game Date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return The static Date
	 */
	public static final Date generateGameDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar start = Calendar.getInstance();
		start.set(year, month, day, hour, minute, second);
		return start.getTime();
	}

	/**
	 * Only for testing clean code
	 * Generates the difference in milliseconds between two dates
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Long generateTimeTracking(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		return end.getTimeInMillis() - start.getTimeInMillis();
	}

}
