package com.deviget.minesweeper.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


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
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class FieldTest {

	@Test
	void testGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(Field.class);
        Validator validator = ValidatorBuilder
                .create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.toString();
        validator.validate(pojoclass);         
        EqualsVerifier.simple().forClass(Field.class).verify();
        Field.builder().toString();
        Field field = new Field(false, true, false, 2);
        Field field2 = new Field(true, false, false, 0);
        Field field3 = new Field(false, false, true, 2);
        Field field4 = new Field(false, false, true, 0);
        Field field5 = new Field(false, false, true, -1);
        field.toString();
        field.revealBoard();
        field2.toString();
        field2.revealBoard();
        field3.toString();
        field3.revealBoard();
        field4.toString();
        field4.revealBoard();
        field5.toString();
        field5.revealBoard();
        Field field6 = Field.builder()
        		.adyacentMines(0)
        		.isFlaged(false)
        		.isMined(true)
        		.isSafe(false)
        		.build();
        field6.revealBoard();
        Field field7 = new Field();
        field7.setAdyacentMines(0);
        field7.setFlaged(true);
        field7.toString();
	}
}
