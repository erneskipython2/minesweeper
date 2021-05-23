package com.deviget.minesweeper.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.deviget.minesweeper.utils.MockedData;
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
@ActiveProfiles("test")
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
        Date start = MockedData.generateGameDate(2021, 4, 20, 23, 43, 0);
        Date end = MockedData.generateGameDate(2021, 4, 20, 23, 44, 0);
        Long timeTracking = MockedData.generateTimeTracking(start, end);
        SessionGame ses1 = new SessionGame(MockedData.ID, 
        		MockedData.USER_ID, 
        		MockedData.STATE,  
        		start, 
        		end, 
        		timeTracking,
        		0,
        		null, 
        		null,
        		null);        
        SessionGame ses2 = SessionGame
        		.builder()
        		.id(MockedData.ID)
        		.userId(MockedData.USER_ID)
        		.state(MockedData.STATE)
        		.startGame(start)
        		.lastUpdate(end)
        		.timeTracking(timeTracking)
        		.build();
        assertTrue(ses1.toString().contains("timeTracking"));
        assertTrue(ses2.toString().contains("state=" + MockedData.STATE));
        assertEquals(ses1, ses2); 
        EqualsVerifier.simple().forClass(SessionGame.class).verify();
        SessionGame.builder().toString();
	}

}
