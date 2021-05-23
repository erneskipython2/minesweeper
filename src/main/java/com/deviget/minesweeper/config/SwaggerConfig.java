package com.deviget.minesweeper.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *Swagger configuration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
	@SuppressWarnings("rawtypes")
	@Bean
    public Docket api() {   
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.deviget.minesweeper.controller")).paths(regex("/.*")).build()
                .apiInfo(new ApiInfo("Minesweeper Rest Api",
                    "Service for play by rest console like view the minesweeper clasic game",
                    "1.0.0", null,
                    new Contact("Erneski Coronado", "http://sr-consultores.com/", "newdavid@gmail.com"), null,
                    null, new ArrayList<VendorExtension>()));
    }
}