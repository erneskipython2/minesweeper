package com.deviget.minesweeper.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class FieldCoordinatesTest {

	@Test
	void testGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(FieldCoordinate.class);
        Validator validator = ValidatorBuilder
                .create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.toString();
        validator.validate(pojoclass);         
        EqualsVerifier.simple().forClass(FieldCoordinate.class).verify();
        FieldCoordinate.builder().toString();
        FieldCoordinate field = new FieldCoordinate(2, 3);
        field.toString();
        FieldCoordinate field6 = FieldCoordinate.builder()
        		.row(2)
        		.column(3)
        		.build();
        FieldCoordinate field7 = new FieldCoordinate();
        field7.setColumn(3);
        field7.setRow(2);
        assertEquals(field6, field7);

	}
}
