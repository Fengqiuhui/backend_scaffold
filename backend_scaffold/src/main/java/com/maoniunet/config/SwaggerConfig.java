package com.maoniunet.config;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .securitySchemes(new ArrayList<>())
//                .enableUrlTemplating(true)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(
                        HttpServletRequest.class,
                        HttpServletResponse.class,
                        WebSession.class,
                        Page.class,
                        Errors.class,
                        HttpSession.class
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "贸牛超人 API",
                "贸牛超人 Api document",
                "1.0",
                "https://www.maoniunet.com",
                new Contact("limingwei", "", "limingwei@maoniunet.com"),
                "",
                "",
                Lists.newArrayList()
        );
    }
}
