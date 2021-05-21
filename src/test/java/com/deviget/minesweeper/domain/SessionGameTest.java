package com.deviget.minesweeper.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
class SessionGameTest {
	
	@Test
	void testGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(SessionGame.class);
        Validator validator = ValidatorBuilder
                .create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.toString();
        validator.validate(pojoclass);
        String id = "123";
        String userId = "1234";
        String state = "PLAYING";     
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(2021, 4, 20, 23, 42, 0);
        end.set(2021, 4, 20, 23, 43, 0);
        Long timeTracking = end.getTimeInMillis() - start.getTimeInMillis();
        SessionGame ses1 = new SessionGame(id, userId, state, start.getTime(), end.getTime(), timeTracking);        
        SessionGame ses2 = SessionGame
        		.builder()
        		.id(id)
        		.userId(userId)
        		.state(state)
        		.startGame(start.getTime())
        		.lastUpdate(end.getTime())
        		.timeTracking(timeTracking)
        		.build();
        assertTrue(ses1.toString().contains("timeTracking=60000"));
        assertEquals(ses1, ses2); 
        EqualsVerifier.simple().forClass(SessionGame.class).verify();
	}

}
