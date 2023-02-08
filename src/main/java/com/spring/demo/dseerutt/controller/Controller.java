package com.spring.demo.dseerutt.controller;

import com.spring.demo.dseerutt.Application;
import com.spring.demo.dseerutt.service.impl.HealthCheckServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/"})
public class Controller {
    private final String HEALTH_CHECK = "health";
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    @Autowired
    private HealthCheckServiceImpl healthCheckServiceImpl;

    @GetMapping(value = StringUtils.EMPTY)
    public String getIndex() {
        return healthCheckServiceImpl.getHealthCheck();
    }

    @GetMapping(value = HEALTH_CHECK)
    public String getHealthCheck() {
        LOGGER.info("Health Check webservice is called");
        return "I am up and running";
    }
}
