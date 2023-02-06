package com.spring.demo.dseerutt.main;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringdemoApplication implements ApplicationRunner {
    private final String HEALTH_CHECK = "/health";

    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        System.out.println("Starting the webservices");
    }

    @RequestMapping(value = HEALTH_CHECK)
    public String getHealthCheck() {
        return "I am up and running";
    }

}
