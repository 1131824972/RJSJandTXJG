package com.example.democlass01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DemoClass01Application {
    public static void main(String[] args) {
        SpringApplication.run(DemoClass01Application.class, args);
    }
}