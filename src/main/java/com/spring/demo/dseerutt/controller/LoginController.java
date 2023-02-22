package com.spring.demo.dseerutt.controller;

import com.spring.demo.dseerutt.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/login"})
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/{username}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<String> getIndex(@PathVariable @NonNull String username) {
        LOGGER.info("Index GET WS was called");
        return new ResponseEntity<>(loginService.getToken(username), HttpStatus.OK);
    }
}
