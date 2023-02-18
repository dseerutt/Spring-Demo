package com.spring.demo.dseerutt.controller;

import com.spring.demo.dseerutt.service.IndexService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/"})
public class IndexController {
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @GetMapping(value = StringUtils.EMPTY, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<String> getIndex() {
        LOGGER.info("Index GET WS was called");
        return new ResponseEntity<>(indexService.getIndex(), HttpStatus.OK);
    }
}
