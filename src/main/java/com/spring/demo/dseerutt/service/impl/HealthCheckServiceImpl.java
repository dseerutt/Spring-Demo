package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.service.HealthCheckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {
    private static final Logger LOGGER = LogManager.getLogger(HealthCheckServiceImpl.class);

    @Override
    public String getHealthCheck() {
        LOGGER.info("Health check is called");
        return "Health";
    }
}
