package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.model.exception.AuthenticationException;
import com.spring.demo.dseerutt.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger LOGGER = LogManager.getLogger(LoginServiceImpl.class);
    private static final String CLIENT_USER = "client";
    private static final String PROVIDER_USER = "provider";
    private static final String CLIENT_TOKEN = "client";
    private static final String PROVIDER_TOKEN = "provider";

    @Override
    public String getToken(String username) {
        if (CLIENT_USER.equals(username))
            return CLIENT_TOKEN;
        if (PROVIDER_USER.equals(username))
            return PROVIDER_TOKEN;
        throw new AuthenticationException("Failed to understand role");
    }
}
