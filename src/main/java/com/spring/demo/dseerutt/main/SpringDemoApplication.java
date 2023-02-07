package com.spring.demo.dseerutt.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringDemoApplication implements ApplicationRunner {
    private final String HEALTH_CHECK = "/health";

    private static final Logger LOGGER = LogManager.getLogger(SpringDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        LOGGER.info("Starting the webservices");
    }

    @RequestMapping(value = HEALTH_CHECK)
    public String getHealthCheck() {
        LOGGER.info("Health Check webservice is called");
        return "I am up and running";
    }

}
