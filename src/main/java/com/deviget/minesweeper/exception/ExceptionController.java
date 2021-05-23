package com.deviget.minesweeper.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception Controller for Custom Business Exception Interception
 * @author Erneski Coronado
 *
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	/**
	 * Manages the USer friendly message for Business Exceptions and the suggested status code
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { MineSweeperException.class })
    protected ResponseEntity<Object> handleBusinessError(RuntimeException ex, WebRequest request) {
		
        String message;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        switch(ex.getMessage()) {
        	case "INVALID_STATE": 
        		message = "Only the next states are valid: STARTED|"
        				+ "PLAYING|"
        				+ "PAUSED|"
        				+ "RESUME|"
        				+ "RESIGNED|"
        				+ "LOSE|"
        				+ "WON";
        		status = HttpStatus.BAD_REQUEST;
        			break;
        	case "INVALID_SESSION": 
        		message = "The resource id requested does not exists";
        		status = HttpStatus.NOT_FOUND;
        		break;
        	case "INVALID_LEVEL":
        		message = "Only the next levels are valid: EASY|NORMAL|HARD|CUSTOM";
        		status = HttpStatus.BAD_REQUEST;
        		break;
        	case "INVALID_MINES_NUMBER":
        		message = "The number of mines should be >1 and <total number of cells";
        		status = HttpStatus.BAD_REQUEST;
        		break;
        	case "GAME_ENDED":
        		message = "The party requested is already ended. Only can be requested for view";
        		status = HttpStatus.NOT_ACCEPTABLE;
        		break;
        	default: 
        		message = "An Unexpected Internal System Error Ocurred. Please try later or contact the support";
        }
        
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }
}
