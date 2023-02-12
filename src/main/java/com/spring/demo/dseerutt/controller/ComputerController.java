package com.spring.demo.dseerutt.controller;

import com.spring.demo.dseerutt.Application;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.service.ComputerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/computer"})
public class ComputerController {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    @Autowired
    private ComputerService computerService;

    @GetMapping(value = StringUtils.EMPTY, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getComputer() {
        Computer computer = computerService.findComputer("2");
        return computer.getBrand();
    }
}
