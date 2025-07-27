package com.arash.edu.statemachinedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "org.springframework.statemachine.data.jpa"
})
@EnableJpaRepositories(basePackages = {
        "org.springframework.statemachine.data.jpa"
})
@SpringBootApplication
public class StateMachineDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StateMachineDemoApplication.class, args);
    }

}
