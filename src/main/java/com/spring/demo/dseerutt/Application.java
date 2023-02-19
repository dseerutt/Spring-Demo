package com.spring.demo.dseerutt;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// run app from cmd line: mvn spring-boot:run
@SpringBootApplication
public class Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void run(ApplicationArguments arg0) throws Exception {
    }

}
