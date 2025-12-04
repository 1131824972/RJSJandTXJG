package com.example.democlass01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 加上 exclude 参数，排除安全自动配置,不用每次都登录
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoClass01Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoClass01Application.class, args);
    }
}