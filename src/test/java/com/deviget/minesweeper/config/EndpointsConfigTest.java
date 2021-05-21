package com.deviget.minesweeper.config;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = EndpointsConfig.class)
@TestPropertySource("classpath:application.properties")
class EndpointsConfigTest {

	@Autowired
    private EndpointsConfig end;

    @Test
    void infoConfigTest() {
        assertEquals("/api", end.getRoot());
        assertEquals("/v1", end.getV1());
        assertThat(end.toString(), containsString("api"));
    }
    
    @Test
    void testGetterSetter() {
        PojoClass pojoclass = PojoClassFactory.getPojoClass(EndpointsConfig.class);
        Validator validator = ValidatorBuilder
                .create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.validate(pojoclass);
    }
    
}
