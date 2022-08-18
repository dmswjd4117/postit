package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        System.out.println("cd test2");
        new SpringApplicationBuilder(MainApplication.class)
                .run(args);
    }

}
