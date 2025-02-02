package com.api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.api"})
@EnableJpaRepositories(basePackages = {"com.api"})
@EntityScan(basePackages = {"com.api"})
@ComponentScan(basePackages = {"com.api"})
public class ApiAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAssignmentApplication.class, args);
    }
}
